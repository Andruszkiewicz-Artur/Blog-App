package com.example.blogapp.core.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
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

    override suspend fun updatePost(postDto: PostDto, idPost: String): PostDto {
        return api.updatePost(
            postDto = postDto,
            idPost = idPost
        )
    }

    override suspend fun createPost(postDto: PostDto): PostDto {
        return  api.createPost(
            postDto = postDto
        )
    }

    override suspend fun createComment(commentDto: CommentDto): CommentDto? {
        return try {
            api.createComment(
                commentDto = commentDto
            )
        } catch (e: Exception) {
            Log.e("API Error", "${e.message}")
            null
        }
    }

    override suspend fun deletePost(idPost: String): String? {
        return try {
            val result = api.deletePost(
                idPost = idPost
            )

            Log.d("Check result delating", "${result}")

            result
        }catch (e: Exception) {
            Log.e("API Error", "${e.message}")
            null
        }
    }
}