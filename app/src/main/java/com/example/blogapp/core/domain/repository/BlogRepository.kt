package com.example.blogapp.core.domain.repository

import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.PostPreviewDto
import com.example.blogapp.core.data.dto.UserBodyDto
import com.example.blogapp.core.data.dto.UserDto

interface BlogRepository {

    suspend fun getPosts(page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun getPostsByTag(tag: String, page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun getPostById(postId: String): PostDto

    suspend fun getCommentsByPost(postId: String): ListDto<CommentDto>

    suspend fun getTags(): ListDto<String>

    suspend fun getUserById(userId: String): UserDto

    suspend fun getPostsByUserId(userId: String, page: Int, limit: Int): ListDto<PostPreviewDto>

    suspend fun createUser(userDto: UserDto): UserDto

    suspend fun getUser(userId: String): UserDto

    suspend fun deleteUser(userId: String): String

    suspend fun getAllUsers(): ListDto<UserDto>

    suspend fun updatePost(postDto: PostDto, idPost: String): PostDto

    suspend fun createPost(postDto: PostDto): PostDto
}