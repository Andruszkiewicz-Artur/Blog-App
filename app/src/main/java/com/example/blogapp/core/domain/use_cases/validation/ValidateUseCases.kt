package com.example.notes.feature_profile.domain.use_case.validationUseCases

import com.example.blogapp.core.domain.use_cases.validation.ValidateContent
import com.example.blogapp.core.domain.use_cases.validation.ValidateData
import com.example.blogapp.core.domain.use_cases.validation.ValidateLink
import com.example.blogapp.core.domain.use_cases.validation.ValidatePhoneNumber
import com.example.blogapp.core.domain.use_cases.validation.ValidationPicture

data class ValidateUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRePassword: ValidateRePassword,
    val validateTerms: ValidateTerms,
    val validateData: ValidateData,
    val validatePhoneNumber: ValidatePhoneNumber,
    val validateContent: ValidateContent,
    val validateLink: ValidateLink,
    val validatePicture: ValidationPicture
)
