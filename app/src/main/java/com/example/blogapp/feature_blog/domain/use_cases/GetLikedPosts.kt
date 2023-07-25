package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetLikedPosts @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(): List<String> {
        return repository.takeAllLikedPosts()
    }

}