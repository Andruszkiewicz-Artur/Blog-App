package com.example.blogapp.core.navigation.graph_profile

sealed class ProfileScreen(
    val route: String
) {

    object Profile: ProfileScreen(
        route = "profile_screen"
    )

    object ChangeData: ProfileScreen(
        route = "change_data_screen"
    )

    object ChangePassword: ProfileScreen(
        route = "change_password"
    )

    object ChangeEmail: ProfileScreen(
        route = "change_email"
    )

}
