package com.example.blogapp.feature_blog.data.remote

import com.example.blogapp.core.Static
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
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

    @GET("post/{id}")
    suspend fun getPostById(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Path("id") postId: String
    ): PostDto
}