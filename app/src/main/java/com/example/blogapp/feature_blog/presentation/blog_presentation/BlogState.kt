package com.example.blogapp.feature_blog.presentation.blog_presentation

import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_blog.domain.model.CommentModel
import com.example.blogapp.feature_blog.domain.model.PostModel

data class BlogState(
    val post: PostModel? = null,
    val user: UserModel? = null,
    val comments: List<CommentModel> = emptyList(),
    val usersList: Map<String, UserModel> = mapOf(),
    val isLoading: Boolean = false,
    val isUserBlog: Boolean = false,
    val isLiked: Boolean = false,
    val isCommentAddPresented: Boolean = false,
    val comment: String = "",
    val commentMessageError: String? = null
)
