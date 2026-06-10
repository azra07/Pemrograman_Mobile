package com.example.songkpop_compose.feature.data.repository

import com.example.songkpop_compose.core.common.UiState
import com.example.songkpop_compose.core.network.ApiResult
import com.example.songkpop_compose.core.network.SafeApiCall
import com.example.songkpop_compose.feature.data.local.MovieDao
import com.example.songkpop_compose.feature.data.mapper.toDomain
import com.example.songkpop_compose.feature.data.mapper.toEntity
import com.example.songkpop_compose.feature.data.remote.MovieApiService
import com.example.songkpop_compose.feature.domain.model.Movie
import com.example.songkpop_compose.feature.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class MovieRepositoryImpl(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao,
    private val apiKey: String
) : MovieRepository, SafeApiCall {

    override fun getPopularMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getMovieById(id: Int): Flow<Movie?> {
        return movieDao.getAllMovies().map { entities ->
            entities.find { it.id == id }?.toDomain()
        }
    }

    override fun fetchAndCacheMovies(): Flow<UiState<Unit>> = flow {
        emit(UiState.Loading)

        val result = safeApiCall { apiService.getPopularMovies(apiKey = apiKey) }

        when (result) {
            is ApiResult.Success -> {
                try {
                    val entities = result.data.results.map { it.toEntity() }
                    movieDao.deleteAllMovies()
                    movieDao.insertMovies(entities)
                    Timber.i("Data API sukses masuk ke caching lokal (Room)")
                    emit(UiState.Success(Unit))
                } catch (e: Exception) {
                    Timber.e(e, "Gagal menulis ke database lokal.")
                    emit(UiState.Error("Gagal menyimpan data lokal."))
                }
            }
            is ApiResult.Error -> {
                Timber.e("API Error Code: ${result.code}, Message: ${result.message}")
                emit(UiState.Error("Koneksi gagal. Menampilkan data offline."))
            }
            is ApiResult.Exception -> {
                Timber.e(result.throwable, "Exception Terjadi saat mengambil data.")
                emit(UiState.Error("Terjadi kesalahan jaringan."))
            }
        }
    }
}