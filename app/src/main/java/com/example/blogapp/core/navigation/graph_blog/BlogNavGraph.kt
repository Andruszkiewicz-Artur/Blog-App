package com.example.blogapp.core.navigation.graph_blog

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blogapp.core.navigation.Graph
import com.example.blogapp.feature_blog.presentation.blogs_presentation.comp.BlogsPresentation

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
            route = BlogScreen.Blog.route
        ) {

        }
    }
}