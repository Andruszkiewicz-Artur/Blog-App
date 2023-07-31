package com.example.blogapp.feature_login_register.data.mapper

import com.example.blogapp.core.data.dto.LocationDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.feature_login_register.domain.model.UserRegistrationModel

fun UserRegistrationModel.toUserDto(): UserDto {
    return UserDto(
        id = "",
        title = "",
        firstName = firstName,
        lastName = lastName,
        gender = "",
        email = email,
        dateOfBirth = "",
        registerDate = "",
        phone = "",
        picture = "",
        location = LocationDto(
            street = "",
            city = "",
            state = "",
            country = ""
        )
    )
}