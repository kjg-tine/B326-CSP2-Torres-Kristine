package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepoImpl implements ArtistRepo {

    private final DbConnection dbConnection;

    public ArtistRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 0";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                artists.add(new Artist(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Get All Artists: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public Artist readArtistById(int id) {
        String query = "SELECT * FROM artists WHERE id = ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            try (ResultSet result = prep.executeQuery()) {
                if (result.next()) {
                    return new Artist(result.getInt("id"), result.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Read Artist By ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Artist> searchArtist(String keyword) {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE name LIKE ? AND is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");
            try (ResultSet result = prep.executeQuery()) {
                while (result.next()) {
                    artists.add(new Artist(result.getInt("id"), result.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Search Artist: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public boolean createArtist(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Artist name is required");
            return false;
        }
        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, name);
            return prep.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Create Artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateArtist(String name, int id) {
        if (id <= 0 || name == null || name.trim().isEmpty()) {
            return false;
        }
        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, name);
            prep.setInt(2, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update Artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveArtist(int id) {
        String query = "UPDATE artists SET is_archived = 1 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Archive Artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreArtist(int id) {
        String query = "UPDATE artists SET is_archived = 0 WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Restore Artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteArtist(int id) {
        String query = "DELETE FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Delete Artist: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Artist> readAllArchivedArtists() {
        List<Artist> archivedArtists = new ArrayList<>();
        String query = "SELECT * FROM artists WHERE is_archived = 1";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {
                archivedArtists.add(new Artist(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Read All Archived Artists: " + e.getMessage());
        }
        return archivedArtists;
    }
}