package com.example.blogapp.feature_blog.data.repository

import com.example.blogapp.feature_blog.data.dto.CommentDto
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.remote.BlogsApi
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val api: BlogsApi
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

}