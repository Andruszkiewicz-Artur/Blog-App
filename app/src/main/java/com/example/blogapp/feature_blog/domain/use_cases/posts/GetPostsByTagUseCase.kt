package com.example.blogapp.feature_blog.domain.use_cases.posts

import com.example.blogapp.feature_blog.data.mapper.toPostPreviewModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostsByTagUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(tag: String, page: Int, limit: Int): Pair<Int, List<PostPreviewModel>> {
        return repository.getPostsByTag(tag, page, limit).toPostPreviewModel()
    }

}