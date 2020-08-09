package com.example.frameworktest.data.repository

import com.example.frameworktest.data.dao.AlbumDao
import com.example.frameworktest.data.db.*
import com.example.frameworktest.data.model.Album
import com.example.frameworktest.ui.albums.AlbumViewParams

class AlbumDbDataSource(
    private val albumDao: AlbumDao
) : AlbumRepository {
    override suspend fun createAlbum(albumViewParams: AlbumViewParams) {
        val albumEntity: AlbumEntity = albumViewParams.toAlbumEntity()
        albumDao.save(albumEntity)
    }

    override suspend fun getAllAlbum(): List<Album> {
        val listAlbumEntity:List<AlbumEntity> = albumDao.getAllAlbum()
        val listAlbum: MutableList<Album> = mutableListOf()

        for (result in listAlbumEntity){
            val album: Album = result.toAlbum()
            listAlbum.add(album)
        }

        return listAlbum
    }
}