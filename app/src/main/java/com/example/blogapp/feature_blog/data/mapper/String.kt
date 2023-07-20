package com.example.blogapp.feature_blog.data.mapper

import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toLocalDataTime(): LocalDateTime {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ISO_INSTANT
        val instant = Instant.from(formatter.parse(this))
        LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    } else {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = formatter.parse(this)
        date!!.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
    }
}