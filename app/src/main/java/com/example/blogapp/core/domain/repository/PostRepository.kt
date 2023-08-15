package com.example.blogapp.core.domain.repository

import android.content.Context
import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result

interface PostRepository {

    suspend fun createPost(postDto: PostDto): Result

    suspend fun takePosts(): Resource<List<PostDto>>

    suspend fun takePost(postId: String): Resource<PostDto>

    suspend fun likePost(postId: String, userId: String): Result

    suspend fun dislikePost(postId: String, userId: String): Result

    suspend fun takeAllLikedPosts(userId: String): Resource<List<String>>

    suspend fun deletePost(postId: String): Result

    suspend fun takeUserPosts(userId: String): Resource<List<PostDto>>
}
