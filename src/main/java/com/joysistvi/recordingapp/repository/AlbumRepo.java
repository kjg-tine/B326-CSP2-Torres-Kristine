package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumRepo {

    List<Album> getAllAlbums();

    Album readAlbumById(int id);

    List<Album> searchAlbum(String keyword);

    boolean createAlbum(String name, String year, int artistId);

    boolean updateAlbum(String name, String year, int artistId, int id);

    boolean deleteAlbum(int id);
}