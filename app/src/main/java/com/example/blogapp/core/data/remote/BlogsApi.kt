package com.example.blogapp.core.data.remote

import com.example.blogapp.core.Static
import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.PostPreviewDto
import com.example.blogapp.core.data.dto.UserBodyDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.dto.UserPreviewDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyApi {

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

    @POST("user/create")
    suspend fun createUser(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Body user: UserDto
    ): UserDto

    @DELETE("user/{id}")
    suspend fun deleteUser(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Body userId: String
    ): String

    @GET("user")
    suspend fun getAllUsers(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Query("limit") limit: Int = 50
    ): ListDto<UserDto>

    @PUT("post/{idPost}")
    suspend fun updatePost(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Body postDto: PostDto,
        @Path("idPost") idPost: String
    ): PostDto

    @POST("post/create")
    suspend fun createPost(
        @Header("app-id") apiKey: String = Static.API_KEY,
        @Body user: UserDto
    ): UserDto
}