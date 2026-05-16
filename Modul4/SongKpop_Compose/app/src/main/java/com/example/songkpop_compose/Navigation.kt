package com.example.songkpop_compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.songkpop_compose.feature.presentation.screens.HomeScreen
import com.example.songkpop_compose.feature.presentation.screens.DetailScreen
import com.example.songkpop_compose.feature.presentation.viewmodel.SongViewModel
import com.example.songkpop_compose.feature.presentation.viewmodel.SongViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val factory = SongViewModelFactory("K-Pop Hits")
    val viewModel: SongViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("detail_screen/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            DetailScreen(albumId = albumId)
        }
    }
}