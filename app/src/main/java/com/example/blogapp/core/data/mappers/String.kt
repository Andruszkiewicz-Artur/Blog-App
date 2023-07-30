package com.example.blogapp.core.data.mappers

import android.util.Log
import com.example.blogapp.core.data.dto.UserDto
import com.google.gson.Gson

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