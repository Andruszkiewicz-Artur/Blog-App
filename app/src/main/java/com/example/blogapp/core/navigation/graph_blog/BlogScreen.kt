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

    object User: BlogScreen(
        route = "user_screen"
    ) {
        fun sendUserId(
            id: String
        ): String {
            return "$route?userId=$id"
        }
    }

    object PostCreateEdit: BlogScreen(
        route = "post_create_edit_screen"
    ) {
        fun sendPostId(
            id: String
        ): String {
            return "$route?postId=$id"
        }
    }

}
