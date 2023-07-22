package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.feature_blog.data.mapper.toPostPreviewModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.PostPreviewModel
import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(page: Int, limit: Int): Pair<Int, List<PostPreviewModel>> {
        return repository.getPosts(page, limit).toPostPreviewModel()
    }

}