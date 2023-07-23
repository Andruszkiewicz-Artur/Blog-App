package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl: FirebaseRepository {

    override suspend fun isCreateUser(email: String, password: String, idUser: String): Boolean {
        val auth = Firebase.auth

        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            Log.d("Check Create User", "${user?.uid}")

            if (user != null) {
                Firebase.database.reference.child(user.uid).child("userId").setValue(idUser).await()
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    override suspend fun forgetPassword(email: String): Boolean {
        return Firebase.auth.sendPasswordResetEmail(email).isSuccessful
    }

}