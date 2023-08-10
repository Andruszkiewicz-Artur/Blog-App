package com.example.blogapp.core.domain.repository

import com.example.blogapp.core.data.dto.ListDto
import com.example.blogapp.core.data.dto.PostDto
import com.example.blogapp.core.domain.unit.Resource
import com.example.blogapp.core.domain.unit.Result

interface PostRepository {

    suspend fun createPost(postDto: PostDto): Result

    suspend fun takePosts(): Resource<List<PostDto>>

}
