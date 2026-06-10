package com.example.songkpop_compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.songkpop_compose.core.database.AppDatabase
import com.example.songkpop_compose.core.network.ApiClient
import com.example.songkpop_compose.feature.data.remote.MovieApiService
import com.example.songkpop_compose.feature.data.repository.MoviePreferencesRepositoryImpl
import com.example.songkpop_compose.feature.data.repository.MovieRepositoryImpl
import com.example.songkpop_compose.feature.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetMovieByIdUseCase
import com.example.songkpop_compose.feature.domain.usecase.GetPopularMoviesUseCase
import com.example.songkpop_compose.feature.domain.usecase.SaveLastOpenedMovieTitleUseCase
import com.example.songkpop_compose.feature.presentation.screens.DetailScreen
import com.example.songkpop_compose.feature.presentation.screens.HomeScreen
import com.example.songkpop_compose.feature.presentation.viewmodel.MovieViewModel
import com.example.songkpop_compose.feature.presentation.viewmodel.MovieViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val movieDao = database.movieDao()
    val apiService = ApiClient.createService(MovieApiService::class.java)
    val apiKey = "afc62091d9ae13b7d0c47e3444ca72d5"

    val movieRepository = MovieRepositoryImpl(apiService, movieDao, apiKey)
    val preferencesRepository = MoviePreferencesRepositoryImpl(context)

    val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    val getMovieByIdUseCase = GetMovieByIdUseCase(movieRepository)
    val getLastOpenedMovieTitleUseCase = GetLastOpenedMovieTitleUseCase(preferencesRepository)
    val saveLastOpenedMovieTitleUseCase = SaveLastOpenedMovieTitleUseCase(preferencesRepository)

    val viewModelFactory = MovieViewModelFactory(
        getPopularMoviesUseCase,
        getMovieByIdUseCase,
        getLastOpenedMovieTitleUseCase,
        saveLastOpenedMovieTitleUseCase,
        categoryName = "Popular Movies"
    )
    val movieViewModel: MovieViewModel = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController = navController, viewModel = movieViewModel)
        }
        composable(
            route = "detail_screen/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(movieId = movieId, viewModel = movieViewModel, navController = navController)
        }
    }
}