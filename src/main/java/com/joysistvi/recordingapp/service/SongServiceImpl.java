package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.repository.SongRepo;

import java.util.List;

public class SongServiceImpl implements SongService {

    private final SongRepo songRepo;

    public SongServiceImpl(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepo.getAllSongs();
    }

    @Override
    public Song getSongById(int id) {
        if (id <= 0) {
            System.out.println("Invalid song ID.");
            return null;
        }

        Song song = songRepo.readSongById(id);

        if (song == null) {
            System.out.println("Song not found.");
        }

        return song;
    }

    @Override
    public List<Song> searchSong(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return songRepo.searchSong(keyword.trim());
    }

    @Override
    public boolean createSong(Song song) {
        if (song == null) {
            System.out.println("Song is required.");
            return false;
        }

        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            System.out.println("Song title is required.");
            return false;
        }

        if (song.getGenre() == null || song.getGenre().trim().isEmpty()) {
            System.out.println("Song genre is required.");
            return false;
        }

        if (song.getAlbumId() <= 0) {
            System.out.println("Invalid album ID.");
            return false;
        }

        return songRepo.createSong(
                song.getTitle().trim(),
                song.getGenre().trim(),
                song.getAlbumId()
        );
    }

    @Override
    public boolean updateSong(Song song) {
        if (song == null || song.getId() <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
            System.out.println("Song title is required.");
            return false;
        }

        if (song.getGenre() == null || song.getGenre().trim().isEmpty()) {
            System.out.println("Song genre is required.");
            return false;
        }

        if (song.getAlbumId() <= 0) {
            System.out.println("Invalid album ID.");
            return false;
        }

        return songRepo.updateSong(
                song.getTitle().trim(),
                song.getGenre().trim(),
                song.getAlbumId(),
                song.getId()
        );
    }

    @Override
    public boolean deleteSong(int id) {
        if (id <= 0) {
            System.out.println("Invalid song ID.");
            return false;
        }

        return songRepo.deleteSong(id);
    }
}