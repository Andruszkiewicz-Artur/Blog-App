package com.example.blogapp.core.data.mappers

import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.feature_blog.domain.model.PostModel

fun List<PostDto>.toPostModel(): List<PostModel> {
    val values: MutableList<PostModel> = mutableListOf()

    this.forEach {
        values.add(
            PostModel(
                id = it.id,
                text = it.text,
                image = it.image,
                likes = it.likes,
                link = it.link,
                tags = it.tags,
                publishDate = it.publishDate.toLocalDateTime(),
                userId = it.userId
            )
        )
    }

    return values
}