package com.example.blogapp.feature_blog.domain.repository

import com.example.blogapp.feature_blog.data.dto.PostPreviewDto

interface BlogRepository {

    fun getPosts(page: Int, limit: Int): List<PostPreviewDto>

}