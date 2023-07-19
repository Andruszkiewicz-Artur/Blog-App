package com.example.blogapp.feature_blog.data.mapper

import android.os.Build
import com.example.blogapp.feature_blog.data.dto.ListDto
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.model.UserPreviewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ListDto<PostPreviewDto>.toPostPreviewModel(): List<PostPreviewModel> {
    val list: MutableList<PostPreviewModel> = mutableListOf()

    data.forEach {
        val localDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ISO_INSTANT
            val instant = Instant.from(formatter.parse(it.publishDate))
            LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        } else {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = formatter.parse(it.publishDate)
            date!!.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
        }

        list.add(
            PostPreviewModel(
                id = it.id,
                text = it.text,
                image = it.image,
                likes = it.likes,
                tags = it.tags,
                publishDate = localDateTime,
                owner = UserPreviewModel(
                    id = it.owner.id,
                    title = it.owner.title,
                    firstName = it.owner.firstName,
                    lastName = it.owner.lastName,
                    picture = it.owner.picture
                )
            )
        )
    }

    return list
}
