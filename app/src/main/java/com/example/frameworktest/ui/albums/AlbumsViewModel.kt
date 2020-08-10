package com.example.frameworktest.ui.albums

import androidx.lifecycle.*
import com.example.frameworktest.data.model.Album
import com.example.frameworktest.data.repository.AlbumRepository
import com.example.frameworktest.data.response.AlbumsBodyResponse
import com.example.frameworktest.service.APIService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AlbumsViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    val albumsLiveData: MutableLiveData<List<Album>> = MutableLiveData()
    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun getAlbums(){
        viewModelScope.launch {
            val albums: List<Album> = getAlbumsDb()

            if(albums.isEmpty()){
                getAlbumsApi()
            } else {
                albumsLiveData.value = albums
                viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
            }
        }
    }

    fun saveAlbumDb(){
        viewModelScope.launch {
            for (result in albumsLiveData.value!!){
                val albumViewParams = AlbumViewParams(
                    userId = result.userId,
                    id = result.id,
                    title = result.title
                )
                albumRepository.createAlbum(albumViewParams)
            }
        }
    }

    private suspend fun getAlbumsDb(): List<Album> {
        return albumRepository.getAllAlbum()
    }

    private fun getAlbumsApi() {
        APIService.service.getAlbums().enqueue(object : Callback<List<AlbumsBodyResponse>> {
            override fun onResponse(
                call: Call<List<AlbumsBodyResponse>>,
                response: Response<List<AlbumsBodyResponse>>
            ) {

                when {
                    response.isSuccessful -> {
                        val Albums: MutableList<Album> = mutableListOf()

                        response.body()?.let { AlbumsBodyResponse ->
                            for (result in AlbumsBodyResponse){
                                val album = result.getAlbumModel()
                                Albums.add(album)
                            }
                        }

                        albumsLiveData.value = Albums
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_POSTS, null)
                        saveAlbumDb()
                    }
                    response.code() === 401 -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, com.example.frameworktest.R.string.error_401)
                    }
                    else -> {
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, com.example.frameworktest.R.string.error_401_generic)
                    }
                }
            }

            override fun onFailure(call: Call<List<AlbumsBodyResponse>>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val VIEW_FLIPPER_POSTS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }

    @Suppress("UNCHECKED_CAST")
    class AlbumViewModelFactory(private val albumRepository: AlbumRepository) :
            ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AlbumsViewModel(albumRepository) as T
        }

    }
}