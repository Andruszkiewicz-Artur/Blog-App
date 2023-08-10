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
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class PostRepositoryImpl: PostRepository {

    override suspend fun createPost(postDto: PostDto): Result {
        return try {
            val database = Firebase.database.reference
            val userId = postDto.userId
            val postId = System.currentTimeMillis().toString()
            val newPost = postDto.copy(
                id = postId,
                publishDate = postDto.publishDate.replaceDataToDatabase()
            )
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
}