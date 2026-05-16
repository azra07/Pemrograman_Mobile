package com.example.songkpop_xml.feature.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songkpop_xml.R
import com.example.songkpop_xml.feature.domain.model.Song
import com.example.songkpop_xml.feature.data.source.SongDataSource
import com.example.songkpop_xml.databinding.FragmentHomeBinding
import com.example.songkpop_xml.feature.presentation.viewmodel.SongViewModel
import com.example.songkpop_xml.feature.presentation.viewmodel.SongViewModelFactory
import com.example.songkpop_xml.feature.presentation.adapter.ListSongAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var recyclerViewState: Parcelable? = null
    private lateinit var viewModel: SongViewModel
    private lateinit var allSongAdapter: ListSongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SongViewModelFactory("K-Pop XML")
        viewModel = ViewModelProvider(this, factory)[SongViewModel::class.java]

        setupRecyclerViewPopular()
        setupRecyclerViewAll()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.songList.collect { list ->
                allSongAdapter.updateData(list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (recyclerViewState != null) {
            binding.rvAll.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }
    }

    override fun onPause() {
        super.onPause()
        recyclerViewState = binding.rvAll.layoutManager?.onSaveInstanceState()
    }

    private fun setupRecyclerViewPopular() {
        binding.rvPopular.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = ListSongAdapter(
            listSong = SongDataSource.listSongs,
            isHorizontal = true,
            onYoutubeClick = { url -> openYoutube(url) },
            onItemClick = { song -> showSelectedSong(song) }
        )
    }

    private fun setupRecyclerViewAll() {
        allSongAdapter = ListSongAdapter(
            listSong = emptyList(),
            onYoutubeClick = { url ->
                Timber.i("Tombol Youtube ditekan")
                openYoutube(url)
            },
            onItemClick = { song ->
                Timber.i("Tombol Detail ditekan untuk: ${song.title}")
                showSelectedSong(song)
            }
        )
        binding.rvAll.layoutManager = LinearLayoutManager(context)
        binding.rvAll.adapter = allSongAdapter
    }

    private fun openYoutube(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(Intent.createChooser(browserIntent, "Buka dengan..."))
        }
    }

    private fun showSelectedSong(song: Song) {
        val bundle = Bundle().apply { putParcelable("EXTRA_SONG", song) }
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}