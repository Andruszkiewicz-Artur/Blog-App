package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.feature_blog.data.mapper.toListOfCommentModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import com.example.blogapp.core.domain.repository.BlogRepository
import javax.inject.Inject

class GetCommentByPostsUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(postId: String): List<CommentModel> {
        return repository.getCommentsByPost(postId).toListOfCommentModel()
    }

}