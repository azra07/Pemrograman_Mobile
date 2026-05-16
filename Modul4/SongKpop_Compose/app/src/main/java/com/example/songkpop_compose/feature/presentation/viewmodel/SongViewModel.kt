package com.example.songkpop_compose.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.songkpop_compose.feature.data.source.AlbumDataSource
import com.example.songkpop_compose.feature.domain.model.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class SongViewModel(private val categoryName: String) : ViewModel() {

    private val _albumList = MutableStateFlow<List<Album>>(emptyList())
    val albumList: StateFlow<List<Album>> = _albumList.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val data = AlbumDataSource.albumList
        _albumList.value = data

        Timber.d("Kategori: $categoryName berhasil dimuat. Total: ${data.size} album.")
        data.forEach { album ->
            Timber.d("Item masuk: ${album.title} - ${album.artist}")
        }
    }

    fun onYoutubeClicked(album: Album) {
        Timber.i("Tombol YouTube (Explicit Intent) ditekan untuk album: ${album.title}")
    }

    fun onDetailClicked(album: Album) {
        Timber.i("Tombol Detail ditekan. Berpindah ke detail album: ${album.title} (${album.year})")
    }
}