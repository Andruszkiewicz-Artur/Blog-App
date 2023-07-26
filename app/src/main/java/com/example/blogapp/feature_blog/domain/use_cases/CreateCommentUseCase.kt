package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.feature_blog.data.mapper.toCommentDto
import com.example.blogapp.feature_blog.data.mapper.toCommentModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(commentModel: CommentModel): CommentModel? {
        return repository.createComment(commentModel.toCommentDto())?.toCommentModel()
    }

}