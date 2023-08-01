package com.example.blogapp.feature_profile.domain.use_cases

data class ProfileUseCases (
    val signOutUseCase: SignOutUseCase,
    val setUpNewPassword: SetUpNewPassword,
    val setUpNewEmailUseCase: SetUpNewEmailUseCase
)