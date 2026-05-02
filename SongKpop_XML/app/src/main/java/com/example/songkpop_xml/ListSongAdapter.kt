package com.example.songkpop_xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songkpop_xml.databinding.ItemSongBinding
import com.example.songkpop_xml.databinding.ItemSongHorizontalBinding

class ListSongAdapter(
    private val listSong: ArrayList<Song>,
    private val isHorizontal: Boolean = false,
    private val onYoutubeClick: (String) -> Unit,
    private val onItemClick: (Song) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class VerticalViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)
    inner class HorizontalViewHolder(val binding: ItemSongHorizontalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isHorizontal) {
            HorizontalViewHolder(ItemSongHorizontalBinding.inflate(inflater, parent, false))
        } else {
            VerticalViewHolder(ItemSongBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int = listSong.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val song = listSong[position]
        val artistWithYear = "${song.artist} (${song.years})"

        if (holder is VerticalViewHolder) {
            with(holder.binding) {
                imgItemPhoto.setImageResource(song.image)
                tvItemTitle.text = song.title
                tvItemArtist.text = artistWithYear
                tvItemDescription.text = song.description
                btnYoutube.setOnClickListener { onYoutubeClick(song.youtubeUrl) }
                btnDetail.setOnClickListener { onItemClick(song) }
                root.setOnClickListener { onItemClick(song) }
            }
        } else if (holder is HorizontalViewHolder) {
            with(holder.binding) {
                imgItemPhoto.setImageResource(song.image)
                tvItemTitle.text = song.title
                tvItemArtist.text = artistWithYear
                tvItemDescription.text = song.description
                btnYoutube.setOnClickListener { onYoutubeClick(song.youtubeUrl) }
                btnDetail.setOnClickListener { onItemClick(song) }
                root.setOnClickListener { onItemClick(song) }
            }
        }
    }
}