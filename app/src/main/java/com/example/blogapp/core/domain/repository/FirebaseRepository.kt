package com.example.blogapp.core.domain.repository

interface FirebaseRepository {

    suspend fun isCreateUser(email: String, password: String, idUser: String): Boolean

}