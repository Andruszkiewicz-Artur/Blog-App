package com.example.blogapp.feature_blog.domain.use_cases.posts

data class PostUseCases(
    val getPostsUseCase: GetPostsUseCase,
    val getPostByIdUseCase: GetPostByIdUseCase,
    val getCommentByPostsUseCase: GetCommentByPostsUseCase,
    val getTagsUseCase: GetTagsUseCase,
    val getPostsByTagUseCase: GetPostsByTagUseCase
)
