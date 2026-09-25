package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepo;

import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepo albumRepo;

    public AlbumServiceImpl(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepo.getAllAlbums();
    }

    @Override
    public Album getAlbumById(int id) {

        if (id <= 0) {
            System.out.println("Invalid album ID.");
            return null;
        }

        Album album = albumRepo.readAlbumById(id);

        if (album == null) {
            System.out.println("Album not found.");
        }

        return album;
    }

    @Override
    public List<Album> searchAlbum(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return albumRepo.searchAlbum(keyword.trim());
    }

    @Override
    public boolean createAlbum(Album album) {

        if (album == null) {
            System.out.println("Album is required.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name is required.");
            return false;
        }

        if (album.getYear() == null || album.getYear().trim().isEmpty()) {
            System.out.println("Album year is required.");
            return false;
        }

        if (album.getArtistId() <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }

        return albumRepo.createAlbum(
                album.getName().trim(),
                album.getYear().trim(),
                album.getArtistId()
        );
    }

    @Override
    public boolean updateAlbum(Album album) {

        if (album == null || album.getId() <= 0) {
            System.out.println("Invalid album ID.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name is required.");
            return false;
        }

        if (album.getYear() == null || album.getYear().trim().isEmpty()) {
            System.out.println("Album year is required.");
            return false;
        }

        if (album.getArtistId() <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }

        return albumRepo.updateAlbum(
                album.getName().trim(),
                album.getYear().trim(),
                album.getArtistId(),
                album.getId()
        );
    }

    @Override
    public boolean deleteAlbum(int id) {

        if (id <= 0) {
            System.out.println("Invalid album ID.");
            return false;
        }

        return albumRepo.deleteAlbum(id);
    }
}