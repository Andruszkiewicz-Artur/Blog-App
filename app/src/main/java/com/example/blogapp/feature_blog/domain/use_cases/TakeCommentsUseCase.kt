package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.CommentRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toListOfCommentModel
import com.example.blogapp.feature_blog.domain.model.CommentModel
import javax.inject.Inject

class TakeCommentsUseCase @Inject constructor(
    private val repository: CommentRepository
) {

    suspend fun invoke(postId: String): Resource<List<CommentModel>> {
        val result = repository.takeComments(postId)

        return when (result) {
            is Resource.Error -> {
                Resource.Error("${result.message}")
            }
            is Resource.Success -> {
                if (result.data == null) return Resource.Error("Problem with taking data")
                Resource.Success(result.data.toListOfCommentModel())
            }
        }
    }

}