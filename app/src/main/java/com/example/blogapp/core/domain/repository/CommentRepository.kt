package com.example.blogapp.core.domain.repository

import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result

interface CommentRepository {

    suspend fun addComment(postId: String, userId: String, commentDto: CommentDto): Result

    suspend fun takeComments(postId: String): Resource<List<CommentDto>>

    suspend fun deletingComment(commentId: String, postId: String): Result
}