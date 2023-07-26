package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.feature_blog.domain.model.dummy_api.CommentModel
import com.example.blogapp.feature_blog.domain.model.dummy_api.UserPreviewModel
import java.time.LocalDateTime

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
                publishDate = it.publishDate?.toLocalDataTime() ?: LocalDateTime.now()
            )
        )
    }

    return list
}

fun CommentModel.toCommentDto(): CommentDto {
    return CommentDto(
        id = null,
        owner = owner.toUserPreviewDto(),
        message = message,
        post = post,
        publishDate = null
    )
}

fun CommentDto.toCommentModel(): CommentModel {
    return CommentModel(
        id = id,
        message = message,
        owner = owner.toUserPreviewModel(),
        post = post,
        publishDate = publishDate?.toLocalDataTime() ?: LocalDateTime.now()
    )
}