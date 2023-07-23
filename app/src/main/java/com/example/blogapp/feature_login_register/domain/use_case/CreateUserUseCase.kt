package com.example.blogapp.feature_login_register.domain.use_case

import com.example.blogapp.core.Global
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.example.blogapp.feature_login_register.data.mapper.toUserBodyDto
import com.example.blogapp.feature_login_register.data.mapper.toUserDto
import com.example.blogapp.feature_login_register.domain.model.UserBodyModel
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: BlogRepository,
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun invoke(userBodyModel: UserBodyModel, password: String): Boolean {
        val user = repository.createUser(userBodyModel.toUserDto()).toUserModel()

        return if (firebaseRepository.isCreateUser(user.email, password, user.id)) {
            Global.user = user
            true
        } else {
            false
        }
    }

}