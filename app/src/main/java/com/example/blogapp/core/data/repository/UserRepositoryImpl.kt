package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl: UserRepository {

    override suspend fun createUser(user: UserDto, password: String): Resource<UserDto> {
        try {
            //Create user and take him id
            Firebase.auth.createUserWithEmailAndPassword(user.email, password).await()

            //Check user id. If is null throw error
            val userId = Firebase.auth.currentUser?.uid
                ?: return Resource.Error(message = "Can`t take user id")

            //Create record in database and check is all success
            val path = Firebase.database.reference.child(userId)
            path.setValue(user).await()

            //Return new user data with userId
            val newUser = user.copy(id = userId)
            return Resource.Success(data = newUser)
        } catch (e: FirebaseAuthException) {
            return Resource.Error(message = "${e.message}")
        } catch (e: FirebaseException) {
            return Resource.Error(message = "${e.message}")
        }

    }

}