package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.repository.PlaylistRepo;
import com.joysistvi.recordingapp.repository.PlaylistSongRepo;

import java.util.List;

public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepo playlistSongRepo;
    private final PlaylistRepo playlistRepo;

    public PlaylistSongServiceImpl(PlaylistSongRepo playlistSongRepo, PlaylistRepo playlistRepo) {
        this.playlistSongRepo = playlistSongRepo;
        this.playlistRepo = playlistRepo;
    }

    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepo.getAllPlaylistSongs();
    }

    @Override
    public List<PlaylistSong> getSongsByPlaylistId(int playlistId) {

        if (playlistId <= 0) {
            return List.of();
        }
        return playlistSongRepo.getSongsByPlaylistId(playlistId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 ||
                songId <= 0) {
            return false;
        }
        return playlistSongRepo.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        if (playlistId <= 0 ||
                songId <= 0) {
            return false;
        }

        return playlistSongRepo.removeSongFromPlaylist(playlistId, songId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId, int userId) {

        if (playlistId <= 0 || songId <= 0 || userId <= 0) {
            return false;
        }

        if (!isPlaylistOwner(playlistId, userId)) {
            System.out.println("You can only modify your own playlist.");
            return false;
        }
        return playlistSongRepo.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId, int userId) {
        if (playlistId <= 0 || songId <= 0 || userId <= 0) {
            return false;
        }

        if (!isPlaylistOwner(playlistId, userId)) {
            System.out.println("You can only modify your own playlist.");
            return false;
        }

        return playlistSongRepo.removeSongFromPlaylist(playlistId, songId);
    }

    private boolean isPlaylistOwner(int playlistId, int userId) {
        Playlist playlist =
                playlistRepo.readPlaylistById(playlistId);

        if (playlist == null) {
            return false;
        }

        return playlist.getUserId() == userId;
    }
}