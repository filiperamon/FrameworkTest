package com.example.frameworktest.ui.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.model.Album
import kotlinx.android.synthetic.main.adapter_posts.view.*

class AlbumAdapter(
    private val albums: List<Album>
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_posts, parent,false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int = albums.count()

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindView(albums[position])
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.tvTitle
        private val userId = itemView.tvUserId
        private val id = itemView.tvId
        private val body = itemView.tvBody
        private val completed = itemView.tvCompleted

        fun bindView(album: Album){
            title.text = album.title
            userId.text = album.userId.toString()
            id.text = album.id.toString()
            body.isGone = true
            completed.isGone = true
        }
    }
}