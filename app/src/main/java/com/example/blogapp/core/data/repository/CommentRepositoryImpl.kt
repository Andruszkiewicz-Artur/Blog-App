package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.example.blogapp.core.data.mappers.replaceTextToDatabase
import com.example.blogapp.core.domain.repository.CommentRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.tasks.await

class CommentRepositoryImpl: CommentRepository {

    override suspend fun addComment(
        postId: String,
        userId: String,
        commentDto: CommentDto
    ): Result {
        return try {
            Firebase.database.reference
                .child("comments")
                .child(postId)
                .child(commentDto.id)
                .setValue(commentDto)
                .await()

            Result(true)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error creating comment", e)
            Result(
                false,
                "Error creating comment"
            )
        }
    }

    override suspend fun takeComments(postId: String): Resource<List<CommentDto>> {
        return try {
            val comments = Firebase.database.reference
                .child("comments")
                .child(postId)
                .get()
                .await()

            val commentsList = mutableListOf<CommentDto>()

            val gson = Gson()
            comments.children.forEachIndexed { index, postSnapshot ->
                try {
                    val commentJson = postSnapshot.value.toString()
                    val commentDto = gson.fromJson(commentJson, CommentDto::class.java)
                    commentsList.add(commentDto)
                } catch (e: JsonSyntaxException) {
                    Log.e("Check JSON Parse Error", "Error parsing JSON: $index", e)
                }
            }

            Resource.Success(commentsList)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error fetching comments", e)
            Resource.Error("Error fetching comments")
        }
    }

    override suspend fun deletingComment(commentId: String, postId: String): Result {
        return try {
            Firebase.database.reference
                .child("comments")
                .child(postId)
                .child(commentId)
                .removeValue()
                .await()

            Result(true)
        } catch (e: Exception) {
            Log.e("Check Firebase Error", "Error fetching deleting comment", e)
            Result(
                false,
                "Error fetching deleting comment"
            )
        }
    }

}