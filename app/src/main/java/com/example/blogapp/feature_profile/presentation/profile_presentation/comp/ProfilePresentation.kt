package com.example.blogapp.feature_profile.presentation.profile_presentation.comp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.feature_profile.presentation.profile_presentation.ProfileViewModel

@Composable
fun ProfilePresentation(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    if(state.user == null) {
        ProfileSignInPresentation(
            navHostController = navHostController
        )
    }

}