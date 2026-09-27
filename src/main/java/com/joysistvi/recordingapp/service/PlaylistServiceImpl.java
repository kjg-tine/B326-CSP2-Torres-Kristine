package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.repository.PlaylistRepo;

import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepo playlistRepo;

    public PlaylistServiceImpl(PlaylistRepo playlistRepo) {
        this.playlistRepo = playlistRepo;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepo.getAllPlaylists();
    }

    @Override
    public List<Playlist> getPlaylistsByUserId(int userId) {
        if (userId <= 0) {
            return List.of();
        }

        return playlistRepo.getPlaylistsByUserId(userId);
    }

    @Override
    public Playlist getPlaylistById(int id) {

        if (id <= 0) {
            return null;
        }

        return playlistRepo.readPlaylistById(id);
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of();
        }

        return playlistRepo.searchPlaylist(keyword.trim());
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {
        if (playlist == null) {
            return false;
        }

        if (playlist.getDateCreated() == null || playlist.getDateCreated().trim().isEmpty()) {
            return false;
        }

        if (playlist.getUserId() <= 0) {
            return false;
        }

        return playlistRepo.createPlaylist(playlist.getDateCreated().trim(), playlist.getUserId());
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {

        if (playlist == null) {
            return false;
        }

        if (playlist.getId() <= 0) {
            return false;
        }

        if (playlist.getDateCreated() == null || playlist.getDateCreated().trim().isEmpty()) {
            return false;
        }

        if (playlist.getUserId() <= 0) {
            return false;
        }

        return playlistRepo.updatePlaylist(playlist.getDateCreated().trim(), playlist.getUserId(), playlist.getId());
    }

    @Override
    public boolean deletePlaylist(int id) {

        if (id <= 0) {
            return false;
        }

        return playlistRepo.deletePlaylist(id);
    }

    @Override
    public boolean deletePlaylist(int playlistId, int userId) {

        if (playlistId <= 0 || userId <= 0) {
            return false;
        }

        Playlist playlist = playlistRepo.readPlaylistById(playlistId);

        if (playlist == null) {

            return false;
        }

        if (playlist.getUserId() != userId) {
            System.out.println("You can only delete your own playlist.");
            return false;
        }

        return playlistRepo.deletePlaylist(playlistId);
    }
}