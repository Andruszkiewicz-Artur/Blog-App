package com.example.blogapp.core.data.mappers

import com.example.blogapp.core.data.dto.LocationDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.model.UserModel

fun UserModel.toUserDto(): UserDto {
    return UserDto(
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        phone = phone,
        dateOfBirth = dateOfBirth?.toString(),
        id = id ?: "",
        title = title,
        email = email,
        picture = picture,
        registerDate = registerDate,
        location = LocationDto(
            country = location.country,
            city = location.city,
            street = location.street,
            state = location.state
        )
    )
}