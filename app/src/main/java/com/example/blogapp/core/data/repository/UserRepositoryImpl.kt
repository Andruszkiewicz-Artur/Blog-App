package com.example.blogapp.core.data.repository

import android.util.Log
import com.example.blogapp.core.Global
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.delay
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
            val path = Firebase.database.reference.child("users").child(userId)
            path.setValue(user.copy(id = userId)).await()

            //Return new user data with userId
            val newUser = user.copy(id = userId)
            return Resource.Success(data = newUser)
        } catch (e: FirebaseAuthException) {
            return Resource.Error(message = "${e.message}")
        } catch (e: FirebaseException) {
            return Resource.Error(message = "${e.message}")
        }

    }

    override suspend fun signOut(){
        try {
            val result = Firebase.auth.signOut()
            Log.d("check result sing out", "$result")
        } catch (e: Exception) {
            Log.d("Error signOut", "${e.message}")
        }
    }

    override suspend fun takeUser(userId: String): Resource<UserDto> {
        try {
            val path = Firebase.database.reference.child("users").child(userId)
            val user = path.get().await().value
                ?: return Resource.Error(message = "Problem with taking data")

            val gson = Gson()
            val userDto = gson.fromJson(user.toString(), UserDto::class.java)

            return Resource.Success(data = userDto)
        } catch (e: FirebaseException) {
            return Resource.Error(message = "${e.message}")
        } catch (e: Exception) {
            return Resource.Error(message = "${e.message}")
        }
    }

    override suspend fun signIn(email: String, password: String, rememberAccount: Boolean): Resource<UserDto> {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            val userId = Firebase.auth.currentUser?.uid
                ?: return Resource.Error(message = "Problem with taking userId")

            if(!rememberAccount) {
                signOut()
            }

            return when (val result = takeUser(userId)) {
                is Resource.Error -> {
                    Resource.Error(message = "${result.message}")
                }

                is Resource.Success -> {
                    Resource.Success(data = result.data)
                }
            }
        } catch (e: FirebaseAuthException) {
            return Resource.Error(message = "${e.message}")
        } catch (e: Exception) {
            return Resource.Error(message = "${e.message}")
        }
    }

    override suspend fun setUpNewPassword(oldPassword: String, newPassword: String): Result {
        try {
            if(Global.user != null) {
                var canChangePassword = false
                var user = Firebase.auth.currentUser
                var logOutUserAfterAll = false

                if(user != null) {
                    val credential = EmailAuthProvider
                        .getCredential(Global.user!!.email, oldPassword)

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            canChangePassword = true
                        }.await()
                } else {
                    Firebase.auth.signInWithEmailAndPassword(Global.user!!.email, oldPassword)
                        .addOnSuccessListener {
                            canChangePassword = true
                            logOutUserAfterAll = true
                        }.await()

                    user = Firebase.auth.currentUser!!
                }

                delay(200)
                if(canChangePassword) {
                    var successfulChangePassword = false

                    user.updatePassword(newPassword)
                        .addOnSuccessListener {
                            successfulChangePassword = true
                        }
                        .await()

                    if (logOutUserAfterAll) signOut()
                    if (successfulChangePassword) return Result(true)
                }

                return Result(
                    successful = false,
                    errorMessage = "Problem with changing password"
                )
            }
        } catch (e: Exception) {
            return Result(
                successful = false,
                errorMessage = "${e.message}"
            )
        }

        return Result(
            successful = false,
            errorMessage = "General problem with global user"
        )
    }

    override suspend fun resetPassword(email: String): Result {
        return try {
            Firebase.auth.sendPasswordResetEmail(email).await()

            Result(
                successful = true
            )
        } catch (e: Exception) {
            Result(
                successful = false,
                errorMessage = "${e.message}"
            )
        }
    }

    override suspend fun changeEmail(newEmail: String, password: String): Result {
        try {
            if(Global.user != null && Global.user?.id != null) {
                var canChangePassword = false
                var user = Firebase.auth.currentUser
                var logOutUserAfterAll = false

                if(user != null) {
                    val credential = EmailAuthProvider
                        .getCredential(Global.user!!.email, password)

                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            canChangePassword = true
                        }.await()
                } else {
                    Firebase.auth.signInWithEmailAndPassword(Global.user!!.email, password)
                        .addOnSuccessListener {
                            canChangePassword = true
                            logOutUserAfterAll = true
                        }.await()

                    user = Firebase.auth.currentUser!!
                }

                delay(200)
                if(canChangePassword) {
                    var successfulChangePassword = false

                    user.updateEmail(newEmail)
                        .addOnSuccessListener {
                            successfulChangePassword = true
                        }.await()

                    delay(100)

                    if (successfulChangePassword) {
                        Firebase.database.reference.child("users").child(Global.user!!.id!!).child("email").setValue(newEmail)
                            .addOnCompleteListener {
                                if(!it.isSuccessful) successfulChangePassword = false
                            }.await()

                        delay(200)
                        Global.user = Global.user?.copy(email = newEmail)
                    }

                    if (logOutUserAfterAll) signOut()
                    if (successfulChangePassword) return Result(true)
                }

                return Result(
                    successful = false,
                    errorMessage = "Problem with changing email"
                )
            }
        } catch (e: Exception) {
            return Result(
                successful = false,
                errorMessage = "${e.message}"
            )
        }

        return Result(
            successful = false,
            errorMessage = "General problem with global user"
        )
    }
}