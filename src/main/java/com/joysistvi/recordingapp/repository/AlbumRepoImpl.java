package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepoImpl implements AlbumRepo {

    private final DbConnection dbConnection;

    public AlbumRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Album> getAllAlbums() {

        List<Album> albums = new ArrayList<>();

        String query = "SELECT * FROM album_tbl";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {

                int id = result.getInt("id");
                String name = result.getString("name");
                String year = result.getString("year");
                int artistId = result.getInt("artist_id");

                albums.add(new Album(
                        id,
                        name,
                        year,
                        artistId
                ));
            }

        } catch (SQLException e) {
            System.err.println("Get All Albums: " + e.getMessage());
        }

        return albums;
    }

    @Override
    public Album readAlbumById(int id) {

        String query = "SELECT * FROM album_tbl WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {

                if (result.next()) {

                    return new Album(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("year"),
                            result.getInt("artist_id")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Album By ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Album> searchAlbum(String keyword) {

        List<Album> albums = new ArrayList<>();

        String query = "SELECT * FROM album_tbl WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    albums.add(new Album(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("year"),
                            result.getInt("artist_id")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Album: " + e.getMessage());
        }

        return albums;
    }

    @Override
    public boolean createAlbum(String name, String year, int artistId) {

        String query = "INSERT INTO album_tbl (name, year, artist_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, name);
            prep.setString(2, year);
            prep.setInt(3, artistId);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create Album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAlbum(String name, String year, int artistId, int id) {

        String query = "UPDATE album_tbl SET name = ?, year = ?, artist_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, name);
            prep.setString(2, year);
            prep.setInt(3, artistId);
            prep.setInt(4, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Album: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAlbum(int id) {

        String query = "DELETE FROM album_tbl WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete Album: " + e.getMessage());
            return false;
        }
    }
}