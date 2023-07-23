package com.example.blogapp.di

import android.app.Application
import android.content.Context
import com.example.blogapp.core.Static
import com.example.blogapp.core.data.remote.DummyApi
import com.example.blogapp.core.data.repository.BlogRepositoryImpl
import com.example.blogapp.core.data.repository.FirebaseRepositoryImpl
import com.example.blogapp.core.domain.repository.BlogRepository
import com.example.blogapp.core.domain.repository.FirebaseRepository
import com.example.blogapp.feature_blog.domain.use_cases.GetCommentByPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.GetPostByIdUseCase
import com.example.blogapp.feature_blog.domain.use_cases.GetPostsByTagUseCase
import com.example.blogapp.feature_blog.domain.use_cases.GetPostsByUserId
import com.example.blogapp.feature_blog.domain.use_cases.GetPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.GetTagsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.GetUserByIdUseCase
import com.example.blogapp.feature_blog.domain.use_cases.PostUseCases
import com.example.blogapp.core.domain.use_cases.validation.ValidateData
import com.example.blogapp.core.domain.use_cases.validation.ValidatePhoneNumber
import com.example.blogapp.feature_login_register.domain.use_case.CreateUserUseCase
import com.example.blogapp.feature_login_register.domain.use_case.ForgetPasswordUseCase
import com.example.blogapp.feature_login_register.domain.use_case.GetAllUsersUseCase
import com.example.blogapp.feature_login_register.domain.use_case.GetUserById
import com.example.blogapp.feature_login_register.domain.use_case.UserUseCases
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): DummyApi {
        return Retrofit.Builder()
            .baseUrl(Static.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBlogsRepository(api: DummyApi): BlogRepository {
        return BlogRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePostsUseCases(repository: BlogRepository): PostUseCases {
        return PostUseCases(
            getPostsUseCase = GetPostsUseCase(repository),
            getPostByIdUseCase = GetPostByIdUseCase(repository),
            getCommentByPostsUseCase = GetCommentByPostsUseCase(repository),
            getTagsUseCase = GetTagsUseCase(repository),
            getPostsByTagUseCase = GetPostsByTagUseCase(repository),
            getUserByIdUseCase = GetUserByIdUseCase(repository),
            getPostsByUserId = GetPostsByUserId(repository)
        )
    }

    @Provides
    @Singleton
    fun provideValidateUseCases(): ValidateUseCases {
        return ValidateUseCases(
            validateEmail = ValidateEmail(),
            validatePassword = ValidatePassword(),
            validateRePassword = ValidateRePassword(),
            validateTerms = ValidateTerms(),
            validateData = ValidateData(),
            validatePhoneNumber = ValidatePhoneNumber()
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(blogRepository: BlogRepository, firebaseRepository: FirebaseRepository): UserUseCases {
        return UserUseCases(
            createUserUseCase = CreateUserUseCase(blogRepository, firebaseRepository),
            getUserById = GetUserById(blogRepository),
            getAllUsersUseCase = GetAllUsersUseCase(blogRepository),
            forgetPasswordUseCase = ForgetPasswordUseCase(firebaseRepository)
        )
    }
}