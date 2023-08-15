package com.example.blogapp.core.data.dto

import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.squareup.moshi.Json

data class PostDto(
    val id: String,
    val text: String,
    val image: String?,
    val likes: Int,
    val link: String?,
    val tags: List<String>?,
    val publishDate: String,
    val userId: String
) {

    @Throws(JsonParseException::class)
    constructor(jsonObject: JsonObject) : this(
        id = jsonObject.get("id").asString,
        text = jsonObject.get("text").asString,
        image = jsonObject.get("image")?.asString,
        likes = jsonObject.get("likes").asInt,
        link = jsonObject.get("link")?.asString,
        tags = jsonObject.get("tags")?.asJsonArray?.map { it.asString },
        publishDate = jsonObject.get("publishDate").asString.replaceDataFromDatabase(),
        userId = jsonObject.get("userId").asString
    )
}
