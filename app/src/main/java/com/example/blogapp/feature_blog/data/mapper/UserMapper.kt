package com.example.blogapp.feature_blog.data.mapper

import com.example.blogapp.feature_blog.data.dto.UserDto
import com.example.blogapp.feature_blog.domain.model.LocationModel
import com.example.blogapp.feature_blog.domain.model.UserModel

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id, title, firstName, lastName, gender, email, dateOfBirth.toLocalDataTime(), registerDate, phone, picture,
        LocationModel(
            location.street, location.city, location.state, location.country, location.timezone
        )
    )
}