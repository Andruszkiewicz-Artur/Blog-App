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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blogapp.R
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.navigation.screen_login_register.LoginRegisterScreen

@Composable
fun ProfileSignInPresentation(
    navHostController: NavHostController
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = context.getString(R.string.YouDontLoginYet),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = context.getString(R.string.UseButtonBelow),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonStandard(
            value = context.getString(R.string.SignIn),
            onClick = { navHostController.navigate(LoginRegisterScreen.Login.route) }
        )

        Text(
            text = context.getString(R.string.Or),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        ButtonStandard(
            value = context.getString(R.string.SignUp),
            onClick = { navHostController.navigate(LoginRegisterScreen.Register.route) }
        )
    }

}