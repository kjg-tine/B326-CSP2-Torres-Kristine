package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepo;

import java.util.List;

public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepo artistRepo;

    public ArtistServiceImpl(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepo.getAllArtists();
    }

    @Override
    public Artist getArtistById(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return null;
        }

        Artist artist = artistRepo.readArtistById(id);
        if (artist == null) {
            System.out.println("Artist not found.");
        }
        return artist;
    }

    @Override
    public List<Artist> searchArtist(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }
        return artistRepo.searchArtist(keyword.trim());
    }

    @Override
    public boolean createArtist(Artist artist) {
        if (artist == null || artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name is required.");
            return false;
        }
        return artistRepo.createArtist(artist.getName().trim());
    }

    @Override
    public boolean updateArtist(Artist artist) {
        if (artist == null || artist.getId() <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name is required.");
            return false;
        }
        return artistRepo.updateArtist(artist.getName().trim(), artist.getId());
    }

    @Override
    public boolean archiveArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }
        return artistRepo.archiveArtist(id);
    }

    @Override
    public boolean restoreArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }
        return artistRepo.restoreArtist(id);
    }

    @Override
    public boolean deleteArtist(int id) {
        if (id <= 0) {
            System.out.println("Invalid artist ID.");
            return false;
        }
        return artistRepo.deleteArtist(id);
    }

    @Override
    public List<Artist> getAllArchivedArtists() {
        return artistRepo.readAllArchivedArtists();
    }
}