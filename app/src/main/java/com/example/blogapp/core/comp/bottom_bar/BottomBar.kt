package com.example.blogapp.core.comp.bottom_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.blogapp.core.domain.model.BottomBarScreenModel
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.core.navigation.graph_profile.ProfileScreen

@Composable
fun BottomBar(
    navHostController: NavHostController,
    currentDestination: NavDestination?
) {
    val screens: List<BottomBarScreenModel> = listOf(
        BottomBarScreenModel(
            title = "Blogs",
            icon = Icons.Outlined.Newspaper,
            route = BlogScreen.Blogs.route
        ),
        BottomBarScreenModel(
            title = "Profile",
            icon = Icons.Outlined.Person2,
            route = ProfileScreen.Profile.route
        )
    )

    val isBottomBar = screens.any { it.route == currentDestination?.route }

    if (isBottomBar) {
        BottomAppBar(
            contentColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navHostController = navHostController
                )
            }
        }
    }
}