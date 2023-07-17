package com.example.blogapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.blogapp.core.navigation.graph_blog.BlogScreen
import com.example.blogapp.core.navigation.graph_blog.blogNavGraph

@Composable
fun RootNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.BLOG
    ) {
        blogNavGraph(navHostController = navHostController)
    }
    
}