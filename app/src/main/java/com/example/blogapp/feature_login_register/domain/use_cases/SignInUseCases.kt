package com.example.blogapp.feature_login_register.domain.use_cases

data class SignInUseCases(
    val createUserUseCase: CreateUserUseCase,
    val signInUseCase: SignInUseCase
)
