package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.dto.UserPreviewDto
import com.example.blogapp.core.data.mappers.toLocalData
import com.example.blogapp.core.domain.model.LocationModel
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id, title, firstName, lastName, gender, email, dateOfBirth?.toLocalData(), registerDate, phone, picture,
        LocationModel(
            location?.street, location?.city, location?.state, location?.country
        )
    )
}

fun UserPreviewModel.toUserPreviewDto(): UserPreviewDto {
    return UserPreviewDto(
        id = id,
        title = title,
        firstName = firstName,
        lastName = lastName,
        picture = picture
    )
}

fun UserPreviewDto.toUserPreviewModel(): UserPreviewModel {
    return UserPreviewModel(
        id = id,
        title = title,
        firstName = firstName,
        lastName = lastName,
        picture = picture
    )
}