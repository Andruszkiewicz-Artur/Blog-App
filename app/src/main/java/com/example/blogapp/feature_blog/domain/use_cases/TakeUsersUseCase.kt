package com.example.blogapp.feature_blog.domain.use_cases

import android.util.Log
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.core.domain.repository.UserRepository
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.feature_blog.data.mapper.toUserModel
import javax.inject.Inject

class TakeUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend fun invoke(list: List<String>): Map<String, UserModel> {
        val newList = mutableMapOf<String, UserModel>()

        list.forEachIndexed { index, idUser ->
            val result = repository.takeUser(idUser)

            when (result) {
                is Resource.Error -> {
                    Log.d("Error takUsersUseCase", "$index: ${result.message}")
                }
                is Resource.Success -> {
                    if (result.data == null) Log.d("Error takeUsersUseCase", "Empty value")
                    else newList[idUser] = result.data.toUserModel()
                }
            }
        }

        return newList
    }

}