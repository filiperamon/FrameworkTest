package com.example.frameworktest.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.model.Post
import kotlinx.android.synthetic.main.adapter_base.view.*

class PostsAdapter(
    private val posts: List<Post>,
    private val onItemClickListener: ((post: Post) -> Unit)
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_base, parent,false)
        return PostsViewHolder(view, onItemClickListener)
    }

    override fun getItemCount() = posts.count()

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bindView(posts[position])
    }

    class PostsViewHolder(itemView: View,
                          private val onItemClickListener: ((post: Post) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.tvTitle
        private val body = itemView.tvBody
        private val completed = itemView.tvCompleted

        fun bindView(post: Post) {
            title.text = post.title
            body.text = post.body
            completed.isGone = true

            itemView.setOnClickListener {
                onItemClickListener.invoke(post)
            }
        }
    }
}