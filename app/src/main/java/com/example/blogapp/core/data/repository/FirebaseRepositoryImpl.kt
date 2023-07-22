package com.example.blogapp.core.data.repository

import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl: FirebaseRepository {

    override suspend fun isCreateUser(email: String, password: String, userId: String): Boolean {
        val auth = Firebase.auth

        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                Firebase.database.reference.child(user.uid).child("userId").setValue(userId).await()
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

}