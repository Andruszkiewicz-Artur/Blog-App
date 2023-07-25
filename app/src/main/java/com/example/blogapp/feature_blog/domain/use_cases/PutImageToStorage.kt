package com.example.blogapp.feature_blog.domain.use_cases

import android.net.Uri
import com.example.blogapp.core.domain.repository.FirebaseRepository
import javax.inject.Inject

class PutImageToStorage @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend fun invoke(uri: Uri): String? {
        return repository.putImageToStorage(uri)
    }

}