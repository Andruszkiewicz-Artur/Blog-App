package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.feature_blog.domain.model.dummy_api.LocationModel
import com.example.blogapp.core.domain.model.UserModel
import java.time.LocalDateTime

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id ?: "", title ?: "", firstName, lastName, gender ?: "", email, dateOfBirth?.toLocalDataTime() ?: LocalDateTime.now(), registerDate ?: "", phone ?: "", picture ?: "",
        LocationModel(
            location?.street ?: "", location?.city ?: "", location?.state ?: "", location?.country ?: "", location?.timezone ?: ""
        )
    )
}