package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepoImpl implements PlaylistRepo {

    private final DbConnection dbConnection;

    public PlaylistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Playlist> getAllPlaylists() {

        List<Playlist> playlists = new ArrayList<>();

        String query = "SELECT * FROM playlists";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {

                int id = result.getInt("id");
                String dateCreated = result.getString("date_created");
                int userId = result.getInt("user_id");

                playlists.add(
                        new Playlist(id, dateCreated, userId)
                );
            }

        } catch (SQLException e) {
            System.err.println("Get All Playlists: " + e.getMessage());
        }

        return playlists;
    }

    @Override
    public Playlist readPlaylistById(int id) {

        String query = "SELECT * FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            try (ResultSet result = prep.executeQuery()) {

                if (result.next()) {

                    return new Playlist(
                            result.getInt("id"),
                            result.getString("date_created"),
                            result.getInt("user_id")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Read Playlist By ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {

        List<Playlist> playlists = new ArrayList<>();

        String query =
                "SELECT * FROM playlists WHERE date_created LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    playlists.add(
                            new Playlist(
                                    result.getInt("id"),
                                    result.getString("date_created"),
                                    result.getInt("user_id")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Search Playlist: " + e.getMessage());
        }

        return playlists;
    }

    @Override
    public boolean createPlaylist(String dateCreated, int userId) {

        String query =
                "INSERT INTO playlists (date_created, user_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, dateCreated);
            prep.setInt(2, userId);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Create Playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePlaylist(
            String dateCreated,
            int userId,
            int id
    ) {

        String query =
                "UPDATE playlists SET date_created = ?, user_id = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, dateCreated);
            prep.setInt(2, userId);
            prep.setInt(3, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Update Playlist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlaylist(int id) {

        String query = "DELETE FROM playlists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Delete Playlist: " + e.getMessage());
            return false;
        }
    }
}