package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.CommentRepository
import com.example.blogapp.core.domain.unit.Result
import com.example.blogapp.feature_blog.data.mapper.toCommentDto
import com.example.blogapp.feature_blog.domain.model.CommentModel
import javax.inject.Inject

class CreatingCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {

    suspend fun invoke(postId: String, userId: String, commentModel: CommentModel): Result {
        return repository.addComment(postId, userId, commentModel.toCommentDto())
    }

}