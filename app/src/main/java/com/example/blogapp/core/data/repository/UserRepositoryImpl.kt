package com.example.blogapp.core.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.blogapp.core.Global
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.data.dto.UserDto
import com.example.blogapp.core.data.mappers.capitalizeFirstLetter
import com.example.blogapp.core.data.mappers.replaceDataFromDatabase
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result
import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class UserRepositoryImpl: UserRepository {

    override suspend fun createUser(user: UserDto, password: String): Resource<UserDto> {
        try {
            Firebase.auth.createUserWithEmailAndPassword(user.email, password).await()

            val userId = Firebase.auth.currentUser?.uid
                ?: return Resource.Error(message = "Can`t take user id")

            val path = Firebase.database.reference.child("users").child(userId)
            path.setValue(
                user.copy(
                    id = userId,
                    registerDate = LocalDate.now().toString()
            )).await()

            return Resource.Success(data = user.copy(
                id = userId,
                firstName = user.firstName.capitalizeFirstLetter(),
                lastName = user.lastName.capitalizeFirstLetter()
            ))
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
            var userDto = gson.fromJson(user.toString(), UserDto::class.java)

            if (userDto.picture != null) {
                val urlImage = takeImage(userDto.picture!!)

                if (urlImage.data != null) {
                    userDto = userDto.copy(
                        picture = urlImage.data,
                    )
                }
            }

            return Resource.Success(data = userDto.copy(
                firstName = userDto.firstName.capitalizeFirstLetter(),
                lastName = userDto.lastName.capitalizeFirstLetter()
            ))
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
                    Resource.Success(data = result.data?.copy(
                        firstName = result.data.firstName.capitalizeFirstLetter(),
                        lastName = result.data.lastName.capitalizeFirstLetter()
                    ))
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

    override suspend fun updateProfile(user: UserDto, previousUserSetUp: UserDto): Resource<UserDto> {
        return try {
            if(user.id.isBlank()) return Resource.Error(message = "Problem with taking user id")

            val database = Firebase.database.reference
            var userDto = user
            val beginPath = "users/${user.id}"
            val updateProfile: MutableMap<String, Any> = hashMapOf()

            if(user.picture != null && user.picture.contains("content://media/picker")) {

                val result = setUpImage(
                    uri = user.picture.toUri()
                )

                if (result.data != null) updateProfile["$beginPath/picture"] = result.data
            }

            if (userDto.firstName != previousUserSetUp.firstName) updateProfile.put("$beginPath/firstName", userDto.firstName)
            if (userDto.lastName != previousUserSetUp.lastName) updateProfile.put("$beginPath/lastName", userDto.lastName)
            if (userDto.gender != previousUserSetUp.gender && !userDto.gender.isNullOrEmpty()) updateProfile.put("$beginPath/gender", userDto.gender!!)
            if (userDto.phone != previousUserSetUp.phone && !userDto.phone.isNullOrEmpty()) updateProfile.put("$beginPath/phone", userDto.phone!!)
            if (userDto.dateOfBirth != previousUserSetUp.dateOfBirth && !userDto.dateOfBirth.isNullOrEmpty()) updateProfile.put("$beginPath/dateOfBirth", userDto.dateOfBirth!!)
            if (userDto.title != previousUserSetUp.title && !userDto.title.isNullOrEmpty()) updateProfile.put("$beginPath/title", userDto.title!!)
            if (userDto.location?.country != previousUserSetUp.location?.country && !userDto.location?.country.isNullOrEmpty()) updateProfile.put("$beginPath/location/country", userDto.location!!.country!!)
            if (userDto.location?.city != previousUserSetUp.location?.city && !userDto.location?.city.isNullOrEmpty()) updateProfile["$beginPath/location/city"] = userDto.location!!.city!!
            if (userDto.location?.street != previousUserSetUp.location?.street && !userDto.location?.street.isNullOrEmpty()) updateProfile.put("$beginPath/location/street", userDto.location!!.street!!)
            if (userDto.location?.state != previousUserSetUp.location?.state && !userDto.location?.state.isNullOrEmpty()) updateProfile.put("$beginPath/location/state", userDto.location!!.state!!)

            Log.d("Check", "${updateProfile}")

            database.updateChildren(updateProfile)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        if (it.exception != null) throw(it.exception!!)
                    }
                }
                .await()


            Resource.Success(data = user.copy(
                firstName = user.firstName.capitalizeFirstLetter(),
                lastName = user.lastName.capitalizeFirstLetter()
            ) )
        } catch (e: Exception) {
            Resource.Error(message = "${e.message}")
        }
    }

    override suspend fun takeAllTags(): Resource<List<String>> {
        return try {
            val path = Firebase.database.reference.child("tags")
            val snapshot = path.get().await()
            val tags = mutableListOf<String>()

            for (child in snapshot.children) {
                tags.add(child.value.toString())
            }

            if (tags.isEmpty()) {
                return Resource.Error(message = "Problem with taking data")
            }
            Resource.Success(data = tags)
        } catch (e: Exception) {
            Resource.Error("${e.message}")
        }
    }

    private suspend fun setUpImage(uri: Uri): Resource<String> {
        return try {
            val photoId = System.currentTimeMillis().toString()

            val photoRef = Firebase.storage.reference
                .child("user")
                .child("$photoId.jpg")

            photoRef.putFile(uri).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
            }.await()

            Resource.Success(data = photoId)
        } catch (e: Exception) {
            Log.e("Error userRepository/setUpImage", "Exception: ${e.message}", e)
            Resource.Error(message = "Problem with set up photo")
        }
    }

    private suspend fun takeImage(idImage: String): Resource<String> {
        return try {
            val photoRef = Firebase.storage.reference
                .child("user")
                .child("$idImage.jpg")
                .downloadUrl
                .await()
                .toString()

            Resource.Success(data = photoRef)
        } catch (e: Exception) {
            Log.e("Error userRepository/takeImage", "Exception: ${e.message}", e)
            Resource.Error(message = "Problem with taking photo")
        }
    }
}