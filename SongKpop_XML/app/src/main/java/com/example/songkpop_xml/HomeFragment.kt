package com.example.songkpop_xml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songkpop_xml.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var recyclerViewState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewPopular()
        setupRecyclerViewAll()
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
        binding.rvPopular.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = ListSongAdapter(
            listSong = SongDataSource.listSongs,
            isHorizontal = true,
            onYoutubeClick = { url -> openYoutube(url) },
            onItemClick = { song -> showSelectedSong(song) }
        )
    }

    private fun setupRecyclerViewAll() {
        binding.rvAll.layoutManager = LinearLayoutManager(context)
        binding.rvAll.adapter = ListSongAdapter(
            listSong = SongDataSource.listSongs,
            isHorizontal = false,
            onYoutubeClick = { url -> openYoutube(url) },
            onItemClick = { song -> showSelectedSong(song) }
        )
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