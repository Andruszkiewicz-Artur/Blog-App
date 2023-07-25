package com.example.blogapp.core

import com.example.blogapp.core.domain.model.UserModel

object Global {

    var user: UserModel? = null
    var likedPosts: List<String> = emptyList()

}