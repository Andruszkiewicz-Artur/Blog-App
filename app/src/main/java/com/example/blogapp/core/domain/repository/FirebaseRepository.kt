package com.example.blogapp.core.domain.repository

import android.net.Uri

interface FirebaseRepository {

    suspend fun isCreateUser(email: String, password: String, idUser: String): Boolean

    suspend fun forgetPassword(email: String): Boolean

    suspend fun logInUser(email: String, password: String): String?

    suspend fun singOut()

    suspend fun getUserId(userId: String): String?

    suspend fun putImageToStorage(uri: Uri): String?
}