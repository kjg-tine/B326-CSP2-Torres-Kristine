package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistService {

    List<Playlist> getAllPlaylists();

    Playlist getPlaylistById(int id);

    List<Playlist> searchPlaylist(String keyword);

    boolean createPlaylist(Playlist playlist);

    boolean updatePlaylist(Playlist playlist);

    boolean deletePlaylist(int id);
}