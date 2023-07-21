package com.example.blogapp.feature_profile.presentation.profile_presentation.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.navigation.screen_login_register.LoginRegisterScreen

@Composable
fun ProfileSignInPresentation(
    navHostController: NavHostController
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "You don`t login yet, or you don`t have account?",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Use button below!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonStandard(
            value = "Sign In",
            onClick = { navHostController.navigate(LoginRegisterScreen.Login.route) }
        )

        Text(
            text = "Or",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        ButtonStandard(
            value = "Register",
            onClick = { navHostController.navigate(LoginRegisterScreen.Register.route) }
        )
    }

}