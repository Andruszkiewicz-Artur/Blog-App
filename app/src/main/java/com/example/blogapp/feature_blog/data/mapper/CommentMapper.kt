package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.feature_blog.data.dto.CommentDto
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel

fun ListDto<CommentDto>.toListOfCommentModel(): List<CommentModel> {
    val list: MutableList<CommentModel> = mutableListOf()

    data.forEach {
        list.add(
            CommentModel(
                id = it.id,
                message = it.message,
                owner = UserPreviewModel(
                    id = it.owner.id,
                    title = it.owner.title,
                    firstName = it.owner.firstName,
                    lastName = it.owner.lastName,
                    picture = it.owner.picture
                ),
                post = it.post,
                publishDate = it.publishDate.toLocalDataTime()
            )
        )
    }

    return list
}