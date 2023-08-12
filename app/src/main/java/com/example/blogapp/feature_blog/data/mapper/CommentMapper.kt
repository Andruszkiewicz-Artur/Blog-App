package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.core.data.dto.CommentDto
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.example.blogapp.core.data.mappers.replaceDataToDatabase
import com.example.blogapp.core.data.mappers.replaceTextFromDatabase
import com.example.blogapp.core.data.mappers.replaceTextToDatabase
import com.example.blogapp.core.data.mappers.toLocalData
import com.example.blogapp.core.data.mappers.toLocalDateTime
import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel
import java.time.LocalDate
import java.time.LocalDateTime


fun CommentModel.toCommentDto(): CommentDto {
    return CommentDto(
        id = id,
        userId = userId,
        message = message.replaceTextToDatabase(),
        postId = postId,
        publishDate = publishDate.toString().replaceDataToDatabase()
    )
}

fun CommentDto.toCommentModel(): CommentModel {
    return CommentModel(
        id = id,
        message = message.replaceTextFromDatabase(),
        userId = userId,
        postId = postId,
        publishDate = publishDate.replaceDataFromDatabase().toLocalDateTime() ?: LocalDateTime.now()
    )
}

fun List<CommentDto>.toListOfCommentModel(): List<CommentModel> {
    val list = mutableListOf<CommentModel>()

    this.forEach {
        list.add( it.toCommentModel() )
    }

    return list
}