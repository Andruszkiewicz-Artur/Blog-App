package com.example.blogapp.feature_blog.data.mapper

import android.annotation.SuppressLint
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.PostPreviewDto
import com.example.blogapp.core.data.dto.UserPreviewDto
import com.example.blogapp.core.data.mappers.replaceDataToDatabase
import com.example.blogapp.core.data.mappers.replaceTextFromDatabase
import com.example.blogapp.core.data.mappers.replaceTextToDatabase
import com.example.blogapp.core.data.mappers.toLocalData
import com.example.blogapp.core.data.mappers.toLocalDateTime
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel
import java.time.LocalDate
import java.time.LocalDateTime

fun PostDto.toPostModel(): PostModel {
    return PostModel(
        id, text.replaceTextFromDatabase(), image, likes, link, tags, publishDate.toLocalDateTime(), userId
    )
}

@SuppressLint("NewApi")
fun PostModel.toPostDto(): PostDto {
    return PostDto(
        id = id ?: "",
        text = text.replaceTextToDatabase(),
        image = image,
        likes = likes,
        link = link,
        tags = tags,
        publishDate = publishDate?.toString()?.replaceDataToDatabase() ?: LocalDateTime.now().toString().replaceDataToDatabase(),
        userId = userId
    )
}