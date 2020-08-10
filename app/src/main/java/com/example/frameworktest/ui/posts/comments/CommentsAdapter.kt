package com.example.frameworktest.ui.posts.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.model.Comments
import kotlinx.android.synthetic.main.adapter_comments.view.*

class CommentsAdapter(
    private val comments: List<Comments>
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_comments, parent,false)
        return CommentsAdapter.CommentsViewHolder(view)
    }

    override fun getItemCount() = comments.count()

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bindView(comments[position])
    }

    class CommentsViewHolder(itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val email = itemView.tvEmai
        private val name = itemView.tvName
        private val body = itemView.tvBodyComments

        fun bindView(comments: Comments) {
            email.text = comments.email
            name.text = comments.name
            body.text = comments.body
        }
    }
}