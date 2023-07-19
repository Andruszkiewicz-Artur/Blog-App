package com.example.blogapp.feature_blog.domain.use_cases.posts

import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(page: Int, limit: Int): List<PostPreviewDto> {
        return repository.getPosts(page, limit).data
    }

}