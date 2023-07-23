package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.PostPreviewDto
import com.example.blogapp.core.data.dto.UserBodyDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.remote.DummyApi
import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val api: DummyApi
): BlogRepository {

    override suspend fun getPosts(page: Int, limit: Int): ListDto<PostPreviewDto> {
        return api.getPostsData(
            page = page,
            limit = limit
        )
    }

    override suspend fun getPostsByTag(
        tag: String,
        page: Int,
        limit: Int
    ): ListDto<PostPreviewDto> {
        return api.getPostsByTag(
            tag = tag,
            page = page,
            limit = limit
        )
    }

    override suspend fun getPostById(postId: String): PostDto {
        return api.getPostById(
            postId = postId
        )
    }

    override suspend fun getCommentsByPost(postId: String): ListDto<CommentDto> {
        return api.getCommentsByPost(
            postId = postId
        )
    }

    override suspend fun getTags(): ListDto<String> {
        return api.getTags()
    }

    override suspend fun getUserById(userId: String): UserDto {
        return api.getUserById(
            userId = userId
        )
    }

    override suspend fun getPostsByUserId(userId: String, page: Int, limit: Int): ListDto<PostPreviewDto> {
        return api.getPostByUserId(
            userId = userId,
            page = page,
            limit = limit
        )
    }

    override suspend fun createUser(userDto: UserDto): UserDto {
        return try {
            api.createUser(
                user = userDto
            )
        } catch (e: Exception) {
            Log.d("check catch api", "${e.printStackTrace()}")
            UserDto(
                id = null,
                title = null,
                firstName = null,
                lastName = null,
                gender = null,
                email = null,
                dateOfBirth = null,
                registerDate = null,
                phone = null,
                picture = null,
                location = null
            )
        }
    }

    override suspend fun getUser(userId: String): UserDto {
        return api.getUserById(
            userId = userId
        )
    }

    override suspend fun deleteUser(userId: String): String {
        return api.deleteUser(
            userId = userId
        )
    }

    override suspend fun getAllUsers(): ListDto<UserDto> {
        return api.getAllUsers()
    }
}