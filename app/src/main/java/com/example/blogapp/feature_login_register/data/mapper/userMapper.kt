package com.example.blogapp.feature_login_register.data.mapper

import com.example.blogapp.core.data.dto.LocationDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.feature_login_register.domain.model.UserRegistrationModel

fun UserRegistrationModel.toUserDto(): UserDto {
    return UserDto(
        id = "",
        title = null,
        firstName = firstName,
        lastName = lastName,
        gender = null,
        email = email,
        dateOfBirth = null,
        registerDate = null,
        phone = null,
        picture = null,
        location = LocationDto(
            street = null,
            city = null,
            state = null,
            country = null
        )
    )
}