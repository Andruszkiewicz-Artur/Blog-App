package com.example.blogapp.feature_blog.data.remote

import com.example.blogapp.core.Static
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BlogsApi {

    @GET("post")
    fun getPostsData(
        @Header("app id") apiKey: String = Static.API_KEY,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): List<PostPreviewDto>
}