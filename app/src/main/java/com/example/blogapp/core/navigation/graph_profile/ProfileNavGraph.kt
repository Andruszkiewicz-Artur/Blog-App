package com.example.blogapp.core.navigation.graph_profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blogapp.core.navigation.Graph
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.feature_profile.presentation.change_password_presentation.comp.ChangePasswordPresentation
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.comp.ChangeUserDataPresentation
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

        composable(
            route = ProfileScreen.ChangeData.route
        ) {
            ChangeUserDataPresentation(
                navHostController = navHostController
            )
        }

        composable(
            route = ProfileScreen.ChangePassword.route
        ) {
            ChangePasswordPresentation(
                navHostController = navHostController
            )
        }
    }
}