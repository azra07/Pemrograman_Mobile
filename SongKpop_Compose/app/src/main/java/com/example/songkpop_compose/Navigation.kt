package com.example.songkpop_compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController = navController)
        }
        composable("detail_screen/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            DetailScreen(albumId = albumId)
        }
    }
}