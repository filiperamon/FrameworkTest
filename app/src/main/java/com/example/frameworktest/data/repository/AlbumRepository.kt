package com.example.frameworktest.data.repository

import com.example.frameworktest.data.model.Album
import com.example.frameworktest.ui.albums.AlbumViewParams

interface AlbumRepository {
    suspend fun createAlbum(albumViewParams: AlbumViewParams)
    suspend fun getAllAlbum(): List<Album>
}