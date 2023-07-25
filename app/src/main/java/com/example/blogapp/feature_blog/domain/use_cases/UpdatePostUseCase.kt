package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.feature_blog.data.mapper.toPostDto
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostModel
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(postModel: PostModel) {
        repository.updatePost(postModel.toPostDto(), postModel.id!!)
    }

}