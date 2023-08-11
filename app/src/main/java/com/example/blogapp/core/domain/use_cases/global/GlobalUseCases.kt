package com.example.blogapp.core.domain.use_cases.global

data class GlobalUseCases(
    val takeAllTagsUseCase: TakeAllTagsUseCase,
    val takeAllLikedPosts: TakeAllLikedPosts
)
