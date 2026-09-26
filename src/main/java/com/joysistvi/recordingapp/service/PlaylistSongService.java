package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {

    List<PlaylistSong> getAllPlaylistSongs();

    List<PlaylistSong> getSongsByPlaylistId(int playlistId);

    boolean addSongToPlaylist(int playlistId, int songId);

    boolean removeSongFromPlaylist(int playlistId, int songId);
}