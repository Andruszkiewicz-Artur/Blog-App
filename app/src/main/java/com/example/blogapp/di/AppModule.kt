package com.example.blogapp.di

import android.app.Application
import com.example.blogapp.core.Static
import com.example.blogapp.feature_blog.data.remote.BlogsApi
import com.example.blogapp.feature_blog.data.repository.BlogRepositoryImpl
import com.example.blogapp.feature_blog.domain.repository.BlogRepository
import com.example.blogapp.feature_blog.domain.use_cases.posts.GetPostsUseCase
import com.example.blogapp.feature_blog.domain.use_cases.posts.PostUseCases
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
            getPostsUseCase = GetPostsUseCase(repository)
        )
    }
}