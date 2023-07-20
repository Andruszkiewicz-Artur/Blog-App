package com.example.blogapp.feature_blog.domain.repository

import com.example.blogapp.feature_blog.data.dto.CommentDto
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.dto.UserDto

interface BlogRepository {

    suspend fun getPosts(page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun getPostsByTag(tag: String, page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun getPostById(postId: String): PostDto

    suspend fun getCommentsByPost(postId: String): ListDto<CommentDto>

    suspend fun getTags(): ListDto<String>

    suspend fun getUserById(userId: String): UserDto

    suspend fun getPostsByUserId(userId: String, page: Int, limit: Int): ListDto<PostPreviewDto>
}