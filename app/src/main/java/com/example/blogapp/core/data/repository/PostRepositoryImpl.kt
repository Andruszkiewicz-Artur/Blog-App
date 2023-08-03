package com.example.blogapp.core.data.repository

import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.domain.repository.PostRepository
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class PostRepositoryImpl: PostRepository {

    override suspend fun createPost(postDto: PostDto): Result{
        return try {
            val userId = postDto.userId
            val postId = System.currentTimeMillis().toString()
            var successfulAddNewPost = false

            Firebase.database.reference.child("posts").child(postId).setValue(
                postDto.copy(
                    id = postId
                )
            )
                .addOnCompleteListener {
                    successfulAddNewPost = it.isSuccessful
                }.await()

            Firebase.database.reference.child("users").child(userId).child("posts").push().setValue(postId)
                .addOnCompleteListener {
                    successfulAddNewPost = it.isSuccessful
                }.await()

            delay(300)

            if(!successfulAddNewPost) return Result(false, "Problem with adding post")

            Result(true)
        } catch (e: Exception) {
            Result(
                false,
                "${e.message}"
            )
        }
    }

}