package com.example.blogapp.feature_blog.data.remote

import com.example.blogapp.core.Static
import com.example.blogapp.feature_blog.data.dto.CommentDto
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.dto.UserDto
import com.example.blogapp.feature_blog.data.dto.UserPreviewDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogsApi {

    @GET("post")
    suspend fun getPostsData(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ListDto<PostPreviewDto>

    @GET("tag/{tag}/post")
    suspend fun getPostsByTag(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("tag") tag: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ListDto<PostPreviewDto>

    @GET("post/{id}")
    suspend fun getPostById(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("id") postId: String
    ): PostDto

    @GET("post/{id}/comment")
    suspend fun getCommentsByPost(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("id") postId: String
    ): ListDto<CommentDto>

    @GET("tag")
    suspend fun getTags(
        @Header("app-id") apiKey: String = Static.API_KEY
    ): ListDto<String>

    @GET("user/{id}")
    suspend fun getUserById(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("id") userId: String
    ): UserDto

    @GET("user/{id}/post")
    suspend fun getPostByUserId(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("id") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): ListDto<PostPreviewDto>
}