package com.example.blogapp.feature_blog.domain.use_cases

data class PostUseCases(
    val getPostsUseCase: GetPostsUseCase,
    val getPostByIdUseCase: GetPostByIdUseCase,
    val getCommentByPostsUseCase: GetCommentByPostsUseCase,
    val getTagsUseCase: GetTagsUseCase,
    val getPostsByTagUseCase: GetPostsByTagUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getPostsByUserId: GetPostsByUserId,
    val getUserFromFirebaseUseCase: GetUserFromFirebaseUseCase,
    val putImageToStorage: PutImageToStorage,
    val updatePostUseCase: UpdatePostUseCase,
    val createPostUseCase: CreatePostUseCase,
    val getLikedPosts: GetLikedPosts,
    val updateLikePostUseCase: UpdateLikePostUseCase,
    val createCommentUseCase: CreateCommentUseCase,
    val deletePostUseCase: DeletePostUseCase
)
