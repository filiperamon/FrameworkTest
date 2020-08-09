package com.example.frameworktest.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frameworktest.R
import com.example.frameworktest.data.db.AppDatabase
import com.example.frameworktest.data.repository.PostDbDataSource

class PostsFragment : Fragment() {

    private val postsViewModel: PostsViewModel by activityViewModels(
        factoryProducer = {
            val database = AppDatabase.getDatabase(requireContext())

            PostsViewModel.PostViewModelFactory(
                postRepository = PostDbDataSource(database.postDao())
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_posts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel.postsLiveData.observe(viewLifecycleOwner, Observer { posts ->
            val recyclePosts: RecyclerView = view.findViewById(R.id.recyclePosts)
            recyclePosts.apply {
                layoutManager = LinearLayoutManager( activity , RecyclerView.VERTICAL,false)
                adapter = PostsAdapter(posts)
            }
        })

        postsViewModel.getPosts()
    }
}