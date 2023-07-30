package com.example.blogapp.di

import com.example.blogapp.core.domain.use_cases.validation.ValidateContent
import com.example.blogapp.core.domain.use_cases.validation.ValidateData
import com.example.blogapp.core.domain.use_cases.validation.ValidateLink
import com.example.blogapp.core.domain.use_cases.validation.ValidatePhoneNumber
import com.example.blogapp.core.domain.use_cases.validation.ValidationPicture
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideValidateUseCases(): ValidateUseCases {
        return ValidateUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRePassword = ValidateRePassword(),
            validateTerms = ValidateTerms(),
            validateData = ValidateData(),
            validatePhoneNumber = ValidatePhoneNumber(),
            validateContent = ValidateContent(),
            validateLink = ValidateLink(),
            validatePicture = ValidationPicture()
        )
    }
}