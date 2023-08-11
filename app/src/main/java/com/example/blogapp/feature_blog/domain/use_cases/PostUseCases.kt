package com.example.blogapp.feature_blog.domain.use_cases

import com.example.blogapp.core.domain.use_cases.global.TakeAllTagsUseCase

data class PostUseCases(
    val takeUserDataUseCase: TakeUserDataUseCase,
    val createPostUseCase: CreatePostUseCase,
    val takePostsUseCase: TakePostsUseCase,
    val takeUsersUseCase: TakeUsersUseCase,
    val takePostUseCase: TakePostUseCase,
    val likePostUseCase: LikePostUseCase,
    val dislikePostUseCase: DislikePostUseCase
)
