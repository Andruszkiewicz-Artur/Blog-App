package com.example.blogapp.feature_login_register.data.mapper

import android.os.Build
import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.LocationDto
import com.example.blogapp.core.data.dto.UserBodyDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.data.mapper.toLocalDataTime
import com.example.blogapp.feature_blog.domain.model.dummy_api.LocationModel
import com.example.blogapp.feature_login_register.domain.model.UserBodyModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun UserBodyModel.toUserDto(): UserDto {
    return UserDto(
        firstName = firstName,
        lastName = lastName,
        email = email,
        id = null,
        title = "",
        gender = "",
        dateOfBirth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Dla API 26+ korzystamy z klasy LocalDate z pakietu java.time
            val currentDate = LocalDate.now()
            currentDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"))
        } else {
            // Dla starszych wersji Androida korzystamy z klasy Date i SimpleDateFormat
            val dateFormatter = SimpleDateFormat("M/d/yyyy", Locale.getDefault())
            dateFormatter.format(Date())
        },
        registerDate = null,
        phone = "999 999 999",
        picture = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fes.pickture.com%2Fstatic%2Fimg%2Fp%2F1557%2F778999_0_original_1434607646.jpg&f=1&nofb=1&ipt=aee03c5537aabe2a0bcf983a759912fed0c684a8babc4c34601b2e35444fa21e&ipo=images",
        location = LocationDto(
            street = "street",
            city = "city",
            state = "state",
            country = "country",
            timezone = "+7:00"
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

fun ListDto<UserDto>.toListUsers(): List<UserModel> {
    return data.map {
        UserModel(
            id = it.id ?: "",
            title = it.title ?: "",
            firstName = it.firstName ?: "",
            lastName = it.lastName ?: "",
            gender = it.gender ?: "",
            email = it.email ?: "",
            dateOfBirth = it.dateOfBirth?.toLocalDataTime() ?: LocalDateTime.now(),
            registerDate = it.registerDate ?: "",
            phone = it.phone ?: "",
            picture = it.picture ?: "",
            location = LocationModel(
                street = it.location?.street ?: "",
                city = it.location?.city ?: "",
                state = it.location?.state ?: "",
                country = it.location?.country ?: "",
                timezone =  it.location?.timezone ?: ""
            )
        )
    }
}