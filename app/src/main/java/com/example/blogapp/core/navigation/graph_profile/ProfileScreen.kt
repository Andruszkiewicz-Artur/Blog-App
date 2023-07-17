package com.example.blogapp.core.navigation.graph_profile

sealed class ProfileScreen(
    val route: String
) {

    object Profile: ProfileScreen(
        route = "profile_screen"
    )

}
