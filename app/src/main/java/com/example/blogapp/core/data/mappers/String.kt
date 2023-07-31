package com.example.blogapp.core.data.mappers

import android.os.Build
import android.util.Log
import com.example.blogapp.core.data.dto.UserDto
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toLocalDataTime(): LocalDateTime {
    return if (this != null && this.isNotBlank() && this.isNotEmpty()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ISO_INSTANT
            val instant = Instant.from(formatter.parse(this))
            LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        } else {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = formatter.parse(this)
            date!!.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
        }
    } else {
        LocalDateTime.now()
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