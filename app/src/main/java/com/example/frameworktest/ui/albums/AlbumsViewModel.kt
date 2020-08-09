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

class AlbumsViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    val albumsLiveData: MutableLiveData<List<Album>> = MutableLiveData()

    fun getAlbums(){
        viewModelScope.launch {
            val albums: List<Album> = getAlbumsDb()

            if(albums.isEmpty()){
                getAlbumsApi()
            } else {
                albumsLiveData.value = albums
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

                if(response.isSuccessful){
                    val Albums: MutableList<Album> = mutableListOf()

                    response.body()?.let { AlbumsBodyResponse ->
                        for (result in AlbumsBodyResponse){
                            val album = Album(
                                userId = result.userId,
                                id = result.id,
                                title = result.title
                            )
                            Albums.add(album)
                        }
                    }

                    albumsLiveData.value = Albums
                    saveAlbumDb()
                }
            }

            override fun onFailure(call: Call<List<AlbumsBodyResponse>>, t: Throwable) {
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    class AlbumViewModelFactory(private val albumRepository: AlbumRepository) :
            ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AlbumsViewModel(albumRepository) as T
        }

    }
}