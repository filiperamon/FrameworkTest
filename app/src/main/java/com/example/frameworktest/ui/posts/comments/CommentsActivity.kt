package com.example.frameworktest.ui.posts.comments

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.db.AppDatabase
import com.example.frameworktest.data.repository.post.comments.CommentsDbDataSource
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.fragment_base.*

class CommentsActivity : AppCompatActivity() {

    private val commentsViewModel: CommentsViewModel by viewModels (
        factoryProducer = {
            val database = AppDatabase.getDatabase(this@CommentsActivity)

            CommentsViewModel.CommandsViewModelFactory(
                commandRepository = CommentsDbDataSource(
                    database.commentDao()
                )
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val postId = intent.getIntExtra(EXTRA_POST_ID, 0)

        commentsViewModel.commentsLiveData.observe(this, Observer {comments ->
            recycleComments.apply {
                layoutManager = LinearLayoutManager( this@CommentsActivity , RecyclerView.VERTICAL,false)
                adapter = CommentsAdapter(comments)
            }
        })

        commentsViewModel.viewFlipperLiveData.observe(this, Observer {
            it?.let {
                vfComments.displayedChild = it
            }
        })

        commentsViewModel.getComments(postId)
    }

    companion object {
        private const val EXTRA_POST_ID = "extra_comments"

        fun getStartIntent(context: Context, postId: Int): Intent {
            return Intent(context, CommentsActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, postId)
            }
        }
    }
}