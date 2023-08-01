package com.example.blogapp.core.data.mappers

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.example.blogapp.core.data.dto.UserDto
import com.google.android.play.core.integrity.e
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@SuppressLint("NewApi")
fun String.toLocalData(): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

   return try {
        LocalDate.parse(this, formatter)
    } catch (e: DateTimeParseException) {
       Log.d("Error toLocalData", "${e.message}")
        null
    }
}

fun String.toUserDto(): UserDto? {
    val gson = Gson()
    return try {
        gson.fromJson(this, UserDto::class.java)
    } catch (e: Exception) {
        Log.d("Error mapper", "${e.message}")
        Log.d("Error mapper", "${e.printStackTrace()}")
        null
    }
}