package com.example.frameworktest.ui.albums

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
import com.example.frameworktest.data.repository.album.AlbumDbDataSource
import kotlinx.android.synthetic.main.fragment_base.*

class AlbumsFragment : Fragment() {

    private val albumsViewModel: AlbumsViewModel by activityViewModels(
        factoryProducer = {
            val database = AppDatabase.getDatabase(requireContext())

            AlbumsViewModel.AlbumViewModelFactory(
                albumRepository = AlbumDbDataSource(
                    database.albumDao()
                )
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
    ): View? = inflater.inflate(R.layout.fragment_base, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        albumsViewModel.albumsLiveData.observe(this, Observer { albums ->
            val recyclePosts: RecyclerView = view.findViewById(R.id.recyclePosts)
            recyclePosts.apply {
                layoutManager = LinearLayoutManager( activity , RecyclerView.VERTICAL,false)
                adapter = AlbumAdapter(albums)
            }
        })

        albumsViewModel.viewFlipperLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { viewFlipper ->
                vfPost.displayedChild = viewFlipper.first

                viewFlipper.second?.let { msgErrorId ->
                    tvError.text = getString(msgErrorId)
                }
            }
        })

        albumsViewModel.getAlbums()
    }
}