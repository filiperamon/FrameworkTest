package com.example.frameworktest.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.model.Post
import kotlinx.android.synthetic.main.adapter_posts.view.*

class PostsAdapter(
    private val posts: List<Post>
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_posts, parent,false)
        return PostsViewHolder(view)
    }

    override fun getItemCount() = posts.count()

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bindView(posts[position])
    }

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.tvTitle
        private val userId = itemView.tvUserId
        private val id = itemView.tvId
        private val body = itemView.tvBody
        private val completed = itemView.tvCompleted

        fun bindView(post: Post) {
            title.text = post.title
            userId.text = post.userId.toString()
            id.text = post.id.toString()
            body.text = post.body
            completed.isGone = true
        }
    }
}