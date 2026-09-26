package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistRepo {

    List<Playlist> getAllPlaylists();

    Playlist readPlaylistById(int id);

    List<Playlist> searchPlaylist(String keyword);

    boolean createPlaylist(String dateCreated, int userId);

    boolean updatePlaylist(String dateCreated, int userId, int id);

    boolean deletePlaylist(int id);
}