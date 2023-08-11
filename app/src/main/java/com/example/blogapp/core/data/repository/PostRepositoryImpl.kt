package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.example.blogapp.core.data.mappers.replaceDataToDatabase
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class PostRepositoryImpl: PostRepository {

    override suspend fun createPost(postDto: PostDto): Result {
        return try {

            val database = Firebase.database.reference
            val userId = postDto.userId
            val postId = postDto.id.ifBlank { System.currentTimeMillis().toString() }
            val newPost = if(postDto.id.isBlank()) postDto.copy(
                id = System.currentTimeMillis().toString()
            ) else {
                postDto
            }
            var successfulAddNewPost = false

            val value: MutableMap<String, Any> = hashMapOf(
                "users/$userId/posts/$postId" to postId,
                "posts/$postId" to newPost
            )

            database.updateChildren(value)
                .addOnCompleteListener {
                    successfulAddNewPost = it.isSuccessful
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

            val gson = Gson()
            posts.children.forEachIndexed { index, postSnapshot ->
                try {
                    val postJson = postSnapshot.value.toString()
                    val postDto = gson.fromJson(postJson, PostDto::class.java)
                    postsList.add(postDto.copy(
                        publishDate = postDto.publishDate.replaceDataFromDatabase()
                    ))
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

            val gson = Gson()
            val postJson = post.value.toString()
            val postDto = gson.fromJson(postJson, PostDto::class.java)

            Resource.Success(
                postDto.copy(
                    publishDate = postDto.publishDate.replaceDataFromDatabase()
            ) )
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
            Result(Firebase.database.reference.child("posts").child(postId).removeValue().isSuccessful)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error deleting post", e)
            Result(
                false,
                "Error deleting post"
            )
        }
    }
}