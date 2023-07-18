package com.example.blogapp.core.navigation.graph_profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blogapp.core.navigation.Graph
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_profile.presentation.profile_presentation.comp.ProfilePresentation

fun NavGraphBuilder.profileNavGraph(
    navHostController: NavHostController
) {

    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreen.Profile.route
    ) {
        composable(
            route = ProfileScreen.Profile.route
        ) {
            ProfilePresentation(
                navHostController = navHostController
            )
        }
    }

}