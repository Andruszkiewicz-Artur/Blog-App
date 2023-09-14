package com.example.blogapp.core.data.repository

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.example.blogapp.core.data.mappers.replaceDataToDatabase
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream

class PostRepositoryImpl: PostRepository {

    companion object {
        private const val TAG = "PostRepositoryImpl"
    }

    override suspend fun createPost(postDto: PostDto): Result {
        val database = Firebase.database.reference
        val userId = postDto.userId
        val postId = postDto.id.ifBlank { System.currentTimeMillis().toString() }
        var newPost = if(postDto.id.isBlank()) postDto.copy(id = postId) else postDto

        var successfulAddNewPost = false

        return try {
            if(postDto.image != null) {
                val imagePath = postDto.image
                Log.d(TAG, "imagePath: $imagePath")

                if(imagePath.contains("content")) {
                    val result = setUpImage(
                        uri = imagePath.toUri()
                    )
                    Log.d(TAG, "result: $result")
                    if (result.data.isNullOrEmpty()) return Result(false, "Problem with adding post")

                    when (result) {
                        is Resource.Error -> {
                            return Result(false, "Problem with adding post")
                        }
                        is Resource.Success -> {
                            newPost = newPost.copy(
                                image = result.data
                            )
                        }
                    }
                }
            }

            database.child("users").child(userId).child("posts").child(postId).setValue(true)
            database.child("posts").child(postId).setValue(newPost)
                .addOnSuccessListener {
                    successfulAddNewPost = true
                }

            delay(200)

            if(!successfulAddNewPost) return Result(false, "Problem with adding post")

            Result(true)
        } catch (e: Exception) {
            Result(
                false,
                "${e.message}"
            )
        }
    }

    override suspend fun takePosts(): Resource<List<PostDto>> {
        return try {
            val posts = Firebase.database.reference.child("posts").get().await()

            val postsList = mutableListOf<PostDto>()

            posts.children.forEachIndexed { index, postSnapshot ->
                try {
                    val post = setData(postSnapshot)

                    Log.d(TAG, "post: $post")

                    postsList.add(post)

                } catch (e: JsonSyntaxException) {
                    Log.e("Check JSON Parse Error", "Error parsing JSON: $index", e)
                }
            }

            Resource.Success(postsList)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error fetching posts", e)
            Resource.Error("Error fetching posts")
        }
    }

    override suspend fun takePost(postId: String): Resource<PostDto> {
        return try {
            val post = Firebase.database.reference.child("posts").child(postId).get().await()

            var postDto = setData(post)

            if (postDto.image != null) {
                val urlImage = takeImage(postDto.image!!)

                if (urlImage.data != null) {
                    postDto = postDto.copy(
                        image = urlImage.data,
                        publishDate = postDto.publishDate.replaceDataFromDatabase()
                    )
                }
            }

            Resource.Success(postDto)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error fetching posts", e)
            Resource.Error("Error fetching posts")
        }
    }

    override suspend fun likePost(postId: String, userId: String): Result {
        return try {
            val database = Firebase.database.reference
            val value: MutableMap<String, Any> = hashMapOf(
                "users/$userId/likesPosts/$postId" to true,
                "posts/$postId/likes" to ServerValue.increment(1)
            )

            database.updateChildren(value).await()

            Result(successful = true)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error liking post", e)
            Result(
                successful = false,
                errorMessage = e.message
            )
        }
    }

    override suspend fun dislikePost(postId: String, userId: String): Result {
        return try {
            val database = Firebase.database.reference
            val value: MutableMap<String, Any> = hashMapOf(
                "users/$userId/likesPosts/$postId" to false,
                "posts/$postId/likes" to ServerValue.increment(-1)
            )

            database.updateChildren(value).await()

            Result(successful = true)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error liking post", e)
            Result(
                successful = false,
                errorMessage = e.message
            )
        }
    }

    override suspend fun takeAllLikedPosts(userId: String): Resource<List<String>> {
        return try {
            val likedPosts = Firebase.database.reference.child("users").child(userId).child("likesPosts").get().await()

            val gson = Gson()
            val likedPostsJson = likedPosts.value.toString()
            val allLikedPosts: Map<String, Boolean> = gson.fromJson(likedPostsJson, object : TypeToken<Map<String, Boolean>>() {}.type)
            val onlyLikedPosts: List<String> = allLikedPosts.filterValues{ it }.keys.toList()

            Resource.Success(onlyLikedPosts)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error fetching liked posts", e)
            Resource.Error("Error fetching posts")
        }
    }

    override suspend fun deletePost(postId: String): Result {
        return try {
            var result = false
            Firebase.database.reference.child("posts").child(postId).removeValue()
                .addOnSuccessListener {
                    result = true
                }

            delay(1000)
            Result(result)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error deleting post", e)
            Result(
                false,
                "Error deleting post"
            )
        }
    }

    override suspend fun takeUserPosts(userId: String): Resource<List<PostDto>> {
        return try {
            val postsIdFromUser = Firebase.database.reference
                .child("users")
                .child(userId)
                .child("posts")
                .get()
                .await()

            val listOfPosts = mutableListOf<PostDto>()

            postsIdFromUser.children.forEach {
                val post = takePost(it.key.toString())

                if (post.data != null) {
                    listOfPosts.add(post.data)
                }
            }

            if (listOfPosts.isEmpty()) {
                return Resource.Error(message = "Problem with taking data")
            }
            Resource.Success(data = listOfPosts)
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }


    private suspend fun setUpImage(uri: Uri): Resource<String> {
        return try {
            val photoId = System.currentTimeMillis().toString()

            val photoRef = Firebase.storage.reference
                .child("post")
                .child("$photoId.jpg")

            photoRef.putFile(uri).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
            }.await()

            Resource.Success(data = photoId)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}", e)
            Resource.Error(message = "Problem with set up photo")
        }
    }

    private suspend fun takeImage(idImage: String): Resource<String> {
        return try {
            val photoRef = Firebase.storage.reference
                .child("post")
                .child("$idImage.jpg")
                .downloadUrl
                .await()
                .toString()

            Resource.Success(data = photoRef)
        } catch (e: Exception) {
            Log.e("Error postRepository/takeImage", "Exception: ${e.message}", e)
            Resource.Error(message = "Problem with taking photo")
        }
    }

    private fun setData(value: DataSnapshot): PostDto {
        val tags: MutableList<String> = mutableListOf()
        val linkData: String = value.child("link").value.toString()

        value.child("tags").children.forEach {
            tags.add(it.value.toString())
        }

        return PostDto(
            id = value.child("id").value.toString(),
            text = value.child("text").value.toString(),
            image = value.child("image").value.toString(),
            likes = value.child("likes").value.toString().toInt(),
            link = if(linkData == "null") null else linkData,
            tags = tags,
            publishDate = value.child("publishDate").value.toString().replaceDataFromDatabase(),
            userId = value.child("userId").value.toString()
        )
    }
}