package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.domain.model.PostModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel

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
                publishDate = it.publishDate.toLocalDataTime(),
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
        id, text, image, likes, link, tags, publishDate.toLocalDataTime(), UserPreviewModel(
            owner.id, owner.title, owner.firstName, owner.lastName, owner.picture
        )
    )
}

fun ListDto<String>.toListStrings(): List<String> {
    return this.data
        .filter { it != null }
        .filter { it.isNotBlank() }
}
