package com.example.blogapp.feature_profile.presentation.profile_presentation.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face3
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.blogapp.core.comp.button.ButtonStandard
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.core.navigation.graph_profile.ProfileScreen
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import com.example.blogapp.feature_profile.presentation.profile_presentation.ProfileViewModel

@Composable
fun ProfilePresentation(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.updateState()
    }

    if(state.user == null) {
        ProfileSignInPresentation(
            navHostController = navHostController
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                if (state.user.picture != null) {
                    AsyncImage(
                        model = state.user.picture,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(200.dp)
                    )
                } else {
                    Icon(
                        imageVector = if (state.user.gender != null && state.user.gender == Gender.Female.toString()) Icons.Outlined.Face3 else Icons.Outlined.Face,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${if(state.user.title != null) state.user.title + ". " else ""} ${state.user.firstName} ${state.user.lastName}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                ButtonStandard(
                    value = "Make a post",
                    onClick = {
                        navHostController.navigate(BlogScreen.PostCreateEdit.route)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ButtonStandard(
                    value = "Change user data",
                    onClick = {
                        navHostController.navigate(ProfileScreen.ChangeData.route)
                    }
                )

                ButtonStandard(
                    value = "Change password",
                    onClick = {
                        navHostController.navigate(ProfileScreen.ChangePassword.route)
                    }
                )

                ButtonStandard(
                    value = "Change email",
                    onClick = {
                        navHostController.navigate(ProfileScreen.ChangeEmail.route)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                ButtonStandard(
                    value = "Log Out",
                    isBordered = true,
                    onClick = {
                        viewModel.logOut()
                    }
                )
            }
        }
    }
}