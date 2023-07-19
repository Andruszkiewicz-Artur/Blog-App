package com.example.blogapp.feature_blog.data.repository

import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.remote.BlogsApi
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class BlogRepositoryImpl (
    private val api: BlogsApi
): BlogRepository {

    override fun getPosts(page: Int, limit: Int): List<PostPreviewDto> {
        return api.getPostsData(
            page = page,
            limit = limit
        )
    }

}