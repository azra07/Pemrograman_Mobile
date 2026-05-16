package com.example.songkpop_xml.feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.songkpop_xml.feature.data.source.SongDataSource
import com.example.songkpop_xml.feature.domain.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class SongViewModel(private val categoryName: String) : ViewModel() {

    private val _songList = MutableStateFlow<List<Song>>(emptyList())
    val songList: StateFlow<List<Song>> = _songList

    init {
        val data = SongDataSource.listSongs
        _songList.value = data
        Timber.Forest.d("Kategori: $categoryName. Data berhasil dimuat: ${data.size} lagu.")
    }

    fun onYoutubeClicked(song: Song) {
        Timber.Forest.i("Tombol Youtube ditekan untuk lagu: ${song.title}")
    }

    fun onDetailClicked(song: Song) {
        Timber.Forest.i("Tombol Detail ditekan untuk: ${song.title}")
    }
}