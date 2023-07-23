package com.example.blogapp.feature_login_register.domain.use_case

data class UserUseCases(
    val createUserUseCase: CreateUserUseCase,
    val getUserById: GetUserById,
    val getAllUsersUseCase: GetAllUsersUseCase
)
