package com.example.songkpop_compose.feature.presentation.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.songkpop_compose.core.common.UiState
import com.example.songkpop_compose.feature.presentation.components.MovieHorizontalItem
import com.example.songkpop_compose.feature.presentation.components.MovieVerticalItem
import com.example.songkpop_compose.feature.presentation.viewmodel.MovieViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel) {
    val movies by viewModel.moviesList.collectAsState()
    val networkState by viewModel.networkState.collectAsState()
    val lastOpenedTitle by viewModel.lastOpenedMovieTitle.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TMDB Popular Movies", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (!lastOpenedTitle.isNullOrEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            text = "Terakhir dibuka: $lastOpenedTitle",
                            modifier = Modifier.padding(12.dp),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            item {
                when (networkState) {
                    is UiState.Loading -> {
                        if (movies.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    is UiState.Error -> {
                        val message = (networkState as UiState.Error).errorMessage
                        LaunchedEffect(networkState) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is UiState.Success -> {}
                }
            }

            if (movies.isNotEmpty()) {
                item {
                    Text(
                        text = "Sorotan Populer",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(movies.take(6)) { movie ->
                            MovieHorizontalItem(
                                movie = movie,
                                onDetailClick = {
                                    Timber.i("Tombol Detail ditekan untuk film: ${movie.title}")
                                    viewModel.saveLastOpenedMovie(movie.title)
                                    navController.navigate("detail_screen/${movie.id}")
                                },
                                onWebsiteClick = {
                                    Timber.i("Tombol Website (Explicit Intent) ditekan untuk film: ${movie.title}")
                                    val webIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.themoviedb.org/movie/${movie.id}")
                                    )
                                    context.startActivity(webIntent)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            if (movies.isNotEmpty()) {
                item {
                    Text(
                        text = "Semua Rekomendasi (Gulir Bawah)",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                }

                items(movies) { movie ->
                    MovieVerticalItem(
                        movie = movie,
                        onDetailClick = {
                            Timber.i("Tombol Detail ditekan untuk film: ${movie.title}")
                            viewModel.saveLastOpenedMovie(movie.title)
                            navController.navigate("detail_screen/${movie.id}")
                        },
                        onWebsiteClick = {
                            Timber.i("Tombol Website (Explicit Intent) ditekan untuk film: ${movie.title}")
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.themoviedb.org/movie/${movie.id}")
                            )
                            context.startActivity(webIntent)
                        }
                    )
                }
            }
        }
    }
}