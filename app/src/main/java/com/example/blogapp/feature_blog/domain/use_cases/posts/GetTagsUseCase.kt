package com.example.blogapp.feature_blog.domain.use_cases.posts

import com.example.blogapp.feature_blog.data.mapper.toListStrings
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend operator fun invoke(): List<String> {
        return repository.getTags().toListStrings()
    }

}