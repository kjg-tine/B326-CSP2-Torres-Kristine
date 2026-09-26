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
    public Playlist getPlaylistById(int id) {

        if (id <= 0) {
            System.out.println("Invalid playlist ID.");
            return null;
        }

        Playlist playlist = playlistRepo.readPlaylistById(id);

        if (playlist == null) {
            System.out.println("Playlist not found.");
        }

        return playlist;
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return playlistRepo.searchPlaylist(keyword.trim());
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {

        if (playlist == null) {
            System.out.println("Playlist is required.");
            return false;
        }

        if (playlist.getDateCreated() == null ||
                playlist.getDateCreated().trim().isEmpty()) {

            System.out.println("Date created is required.");
            return false;
        }

        if (playlist.getUserId() <= 0) {
            System.out.println("Invalid user ID.");
            return false;
        }

        return playlistRepo.createPlaylist(
                playlist.getDateCreated().trim(),
                playlist.getUserId()
        );
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {

        if (playlist == null || playlist.getId() <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        if (playlist.getDateCreated() == null ||
                playlist.getDateCreated().trim().isEmpty()) {

            System.out.println("Date created is required.");
            return false;
        }

        if (playlist.getUserId() <= 0) {
            System.out.println("Invalid user ID.");
            return false;
        }

        return playlistRepo.updatePlaylist(
                playlist.getDateCreated().trim(),
                playlist.getUserId(),
                playlist.getId()
        );
    }

    @Override
    public boolean deletePlaylist(int id) {

        if (id <= 0) {
            System.out.println("Invalid playlist ID.");
            return false;
        }

        return playlistRepo.deletePlaylist(id);
    }
}