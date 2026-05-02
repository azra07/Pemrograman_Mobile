package com.example.songkpop_xml

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.songkpop_xml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("EXTRA_SONG", Song::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("EXTRA_SONG")
        }

        song?.let {
            binding.tvItemTitle.text = it.title
            binding.tvItemYears.text = it.years
            binding.tvItemArtist.text = it.artist
            binding.tvItemDesc.text = it.description
            binding.imgPoster.setImageResource(it.image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}