package com.example.blogapp.core.navigation.graph_blog

sealed class BlogScreen(
    val route: String
) {

    object Blogs: BlogScreen(
        route = "blogs_screen"
    )

    object Blog: BlogScreen(
        route = "blog_screen"
    ) {
        fun sendPostId(
            id: String
        ): String {
            return "$route?postId=$id"
        }
    }

}
