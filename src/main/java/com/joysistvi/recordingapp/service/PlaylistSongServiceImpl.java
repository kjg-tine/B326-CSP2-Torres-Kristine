package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.repository.PlaylistSongRepo;

import java.util.List;

public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepo playlistSongRepo;

    public PlaylistSongServiceImpl(PlaylistSongRepo playlistSongRepo) {
        this.playlistSongRepo = playlistSongRepo;
    }

    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepo.getAllPlaylistSongs();
    }

    @Override
    public List<PlaylistSong> getSongsByPlaylistId(int playlistId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return List.of();
        }

        return playlistSongRepo.getSongsByPlaylistId(playlistId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        if (songId <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        return playlistSongRepo.addSongToPlaylist(
                playlistId,
                songId
        );
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {

        if (playlistId <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        if (songId <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        return playlistSongRepo.removeSongFromPlaylist(
                playlistId,
                songId
        );
    }
}