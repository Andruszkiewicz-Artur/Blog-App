package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.repository.FirebaseRepository
import javax.inject.Inject

class UpdateLikePostUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend fun invoke(listLike: List<String>): List<String>? {
        return repository.updateLikeList(listLike)
    }

}