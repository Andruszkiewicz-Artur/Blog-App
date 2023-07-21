package com.example.blogapp.di

import android.app.Application
import com.example.blogapp.core.Static
import com.example.blogapp.feature_blog.data.remote.BlogsApi
import com.example.blogapp.feature_blog.data.repository.BlogRepositoryImpl
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetCommentByPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostByIdUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsByTagUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsByUserId
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetTagsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetUserByIdUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.PostUseCases
import com.example.blogapp.feature_login_register.domain.use_cases.validation.ValidateData
import com.example.blogapp.feature_login_register.domain.use_cases.validation.ValidatePhoneNumber
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateEmail
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidatePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateRePassword
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateTerms
import com.example.notes.feature_profile.domain.use_case.validationUseCases.ValidateUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideWeatherApi(): BlogsApi {
        return Retrofit.Builder()
            .baseUrl(Static.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBlogsRepository(api: BlogsApi): BlogRepository {
        return BlogRepositoryImpl(api)
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
}