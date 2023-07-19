package com.example.blogapp.feature_blog.data.repository

import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.remote.BlogsApi
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import kotlinx.coroutines.flow.flow
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

}