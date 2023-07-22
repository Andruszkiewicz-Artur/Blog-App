package com.example.blogapp.core.comp.bottom_bar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.blogapp.feature_blog.domain.model.BottomBarScreenModel

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreenModel,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    val isCurrent = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    NavigationBarItem(
        alwaysShowLabel = false,
        label = {
            Text(
                text = screen.title,
                color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.title,
                tint = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        },
        selected = isCurrent,
        onClick = {
            navHostController.navigate(screen.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}