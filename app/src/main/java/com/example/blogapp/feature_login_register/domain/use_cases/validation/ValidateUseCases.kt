package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.feature_login_register.domain.use_cases.validation.ValidateData
import com.example.blogapp.feature_login_register.domain.use_cases.validation.ValidatePhoneNumber

data class ValidateUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRePassword: ValidateRePassword,
    val validateTerms: ValidateTerms,
    val validateData: ValidateData,
    val validatePhoneNumber: ValidatePhoneNumber
)
