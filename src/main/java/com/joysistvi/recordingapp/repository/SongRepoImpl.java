package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepoImpl implements SongRepo {

    private final DbConnection dbConnection;

    public SongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM songs_tbl";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String genre = result.getString("genre");
                int albumId = result.getInt("album_id");

                songs.add(new Song(id, title, genre, albumId));
            }

        } catch (SQLException e) {
            System.err.println("Get All Songs: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public Song readSongById(int id) {
        String query = "SELECT * FROM songs_tbl WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {

                if (result.next()) {
                    return new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("genre"),
                            result.getInt("album_id")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Song By ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Song> searchSong(String keyword) {
        List<Song> songs = new ArrayList<>();

        String query = "SELECT * FROM songs_tbl WHERE title LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {
                    songs.add(new Song(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("genre"),
                            result.getInt("album_id")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Song: " + e.getMessage());
        }

        return songs;
    }

    @Override
    public boolean createSong(String title, String genre, int albumId) {

        String query = "INSERT INTO songs_tbl (title, genre, album_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, title);
            prep.setString(2, genre);
            prep.setInt(3, albumId);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSong(String title, String genre, int albumId, int id) {

        String query = "UPDATE songs_tbl SET title = ?, genre = ?, album_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, title);
            prep.setString(2, genre);
            prep.setInt(3, albumId);
            prep.setInt(4, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Song: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteSong(int id) {

        String query = "DELETE FROM songs_tbl WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete Song: " + e.getMessage());
            return false;
        }
    }
}