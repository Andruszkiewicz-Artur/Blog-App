package com.example.blogapp.core.navigation.screen_login_register

sealed class LoginRegisterScreen(
    val route: String
) {
    object Login: LoginRegisterScreen(
        route = "login_screen"
    )

    object Register: LoginRegisterScreen(
        route = "register_screen"
    )

    object ForgetPassword: LoginRegisterScreen(
        route = "forget_password_screen"
    )
}
