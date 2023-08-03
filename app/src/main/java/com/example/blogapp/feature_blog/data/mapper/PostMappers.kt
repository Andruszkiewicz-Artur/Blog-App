package com.example.blogapp.feature_blog.data.mapper

import android.annotation.SuppressLint
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.PostPreviewDto
import com.example.blogapp.core.data.dto.UserPreviewDto
import com.example.blogapp.core.data.mappers.toLocalData
import com.example.blogapp.core.data.mappers.toLocalDateTime
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel
import java.time.LocalDate
import java.time.LocalDateTime

@SuppressLint("NewApi")
fun ListDto<PostPreviewDto>.toPostPreviewModel(): Pair<Int, List<PostPreviewModel>> {
    val list: MutableList<PostPreviewModel> = mutableListOf()

    data.forEach {
        list.add(
            PostPreviewModel(
                id = it.id,
                text = it.text,
                image = it.image,
                likes = it.likes,
                tags = it.tags,
                publishDate = it.publishDate.toLocalData() ?: LocalDate.now(),
                owner = UserPreviewModel(
                    id = it.owner.id,
                    title = it.owner.title,
                    firstName = it.owner.firstName,
                    lastName = it.owner.lastName,
                    picture = it.owner.picture
                )
            )
        )
    }

    return Pair(total/limit, list)
}

fun PostDto.toPostModel(): PostModel {
    return PostModel(
        id, text, image, likes, link, tags, publishDate.toLocalDateTime(), userId
    )
}

fun ListDto<String>.toListStrings(): List<String> {
    return this.data
        .filter { it != null }
        .filter { it.isNotBlank() }
}

@SuppressLint("NewApi")
fun PostModel.toPostDto(): PostDto {
    return PostDto(
        id = id ?: "",
        text = text,
        image = image,
        likes = likes,
        link = link,
        tags = tags,
        publishDate = publishDate?.toString() ?: LocalDateTime.now().toString(),
        userId = userId
    )
}