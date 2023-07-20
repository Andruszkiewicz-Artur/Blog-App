package com.example.blogapp.feature_blog.domain.use_cases.posts

import android.util.Log
import com.example.blogapp.feature_blog.data.dto.PostPreviewDto
import com.example.blogapp.feature_blog.data.mapper.toPostPreviewModel
import com.example.blogapp.feature_blog.domain.model.PostPreviewModel
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import javax.inject.Inject

class GetPostsByUserId @Inject constructor(
    private val repository: BlogRepository
) {

    suspend fun invoke(userId: String, page: Int, limit: Int): Pair<Int, List<PostPreviewModel>> {
        var posts = repository.getPostsByUserId(userId, page, limit)

        if (posts.total < page * limit) {
            val newLimit = posts.total - ((page - 1) * limit)
            posts = repository.getPostsByUserId(userId, (posts.total/newLimit) - 1, newLimit)
        }

        return posts.toPostPreviewModel()
    }

}