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
@SuppressLint("NewApi")
fun String.toLocalDateTime(): LocalDateTime? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'kk:mm:ss.SSSSSS");

    return try {
        LocalDateTime.parse(this, formatter)
    } catch (e: DateTimeParseException) {
        Log.d("Error toLocalDataTime", "${e.message}")
        null
    }
}

fun String.replaceDataToDatabase(): String {
    return this.replace('-', 'm').replace(':', 'd').replace('.', 'e')
}

fun String.replaceDataFromDatabase(): String {
    return this.replace('m', '-').replace('d', ':').replace('e', '.')
}