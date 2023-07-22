package com.example.blogapp.feature_login_register.data.mapper

import com.example.blogapp.core.data.dto.LocationDto
import com.example.blogapp.core.data.dto.UserBodyDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.feature_login_register.domain.model.UserBodyModel

fun UserBodyModel.toUserDto(): UserDto {
    return UserDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        id = null,
        title = null,
        gender = null,
        dateOfBirth = null,
        registerDate = null,
        phone = null,
        picture = null,
        location = LocationDto(
            street = null,
            city = null,
            state = null,
            country = null,
            timezone = null
        )
    )
}

fun UserBodyModel.toUserBodyDto(): UserBodyDto {
    return UserBodyDto(
        firstName = firstName,
        lastName = lastName,
        email = email
    )
}