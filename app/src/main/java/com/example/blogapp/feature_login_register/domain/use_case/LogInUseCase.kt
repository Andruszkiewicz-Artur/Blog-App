package com.example.blogapp.feature_login_register.domain.use_case

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val blogRepository: BlogRepository,
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun invoke(email: String, password: String, isRemember: Boolean): UserModel? {
        val userId = firebaseRepository.logInUser(email, password)

        return if (userId != null) {
            if(!isRemember) {
                firebaseRepository.singOut()
            }
            blogRepository.getUserById(userId).toUserModel()
        } else {
            null
        }
    }

}