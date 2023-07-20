package com.example.blogapp.feature_blog.domain.repository

import com.example.blogapp.feature_blog.data.dto.CommentDto
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto

interface BlogRepository {

    suspend fun getPosts(page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun getPostById(postId: String): PostDto

    suspend fun getCommentsByPost(postId: String): ListDto<CommentDto>

}