package com.example.blogapp.core.navigation.screen_login_register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.blogapp.core.navigation.Graph
import com.example.blogapp.core.navigation.graph_profile.ProfileScreen

fun NavGraphBuilder.loginRegisterNavGraph(
    navHostController: NavHostController
) {

    navigation(
        route = Graph.LOGIN_REGISTER,
        startDestination = LoginRegisterScreen.Login.route
    ) {
        composable(
            route = LoginRegisterScreen.Login.route
        ) {

        }
        composable(
            route = LoginRegisterScreen.Register.route
        ) {

        }
        composable(
            route = LoginRegisterScreen.ForgetPassword.route
        ) {

        }
    }

}