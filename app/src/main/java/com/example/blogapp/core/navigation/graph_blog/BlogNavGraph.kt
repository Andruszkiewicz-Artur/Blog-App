package com.example.blogapp.core.navigation.graph_blog

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.blogapp.core.navigation.Graph
import com.example.blogapp.feature_blog.presentation.blog_presentation.comp.BlogPresentation
import com.example.blogapp.feature_blog.presentation.blogs_presentation.comp.BlogsPresentation
import com.example.blogapp.feature_blog.presentation.user_presentation.comp.UserPresentation

fun NavGraphBuilder.blogNavGraph(
    navHostController: NavHostController
) {

    navigation(
        route = Graph.BLOG,
        startDestination = BlogScreen.Blogs.route
    ) {
        composable(
            route = BlogScreen.Blogs.route
        ) {
            BlogsPresentation(
                navHostController = navHostController
            )
        }

        composable(
            route = BlogScreen.Blog.route + "?postId={postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            BlogPresentation(
                navHostController = navHostController
            )
        }

        composable(
            route = BlogScreen.User.route + "?userId={userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            UserPresentation(
                navHostController = navHostController
            )
        }
    }
}