package com.example.blogapp.core.data.extension

import android.content.Context

fun getString(value: String?, context: Context): String? {
    if (value.isNullOrEmpty()) return null
    return context.getString(value.toInt())
}