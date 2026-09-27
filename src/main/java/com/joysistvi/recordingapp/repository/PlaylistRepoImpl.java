package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        String sql =
                "SELECT * FROM playlists";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                String dateCreated =
                        resultSet.getString("date_created");

                int userId =
                        resultSet.getInt("user_id");

                playlists.add(
                        new Playlist(
                                id,
                                dateCreated,
                                userId
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error retrieving playlists: "
                            + e.getMessage()
            );
        }

        return playlists;
    }

    // ========================================
    // GET PLAYLISTS BY USER ID
    // ========================================

    @Override
    public List<Playlist> getPlaylistsByUserId(int userId) {

        List<Playlist> playlists =
                new ArrayList<>();

        String sql =
                "SELECT * FROM playlists WHERE user_id = ?";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                String dateCreated =
                        resultSet.getString("date_created");

                int foundUserId =
                        resultSet.getInt("user_id");

                playlists.add(
                        new Playlist(
                                id,
                                dateCreated,
                                foundUserId
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error retrieving user playlists: "
                            + e.getMessage()
            );
        }

        return playlists;
    }

    // ========================================
    // GET PLAYLIST BY ID
    // ========================================

    @Override
    public Playlist readPlaylistById(int id) {

        String sql =
                "SELECT * FROM playlists WHERE id = ?";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(
                    1,
                    id
            );

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                int playlistId =
                        resultSet.getInt("id");

                String dateCreated =
                        resultSet.getString("date_created");

                int userId =
                        resultSet.getInt("user_id");

                return new Playlist(
                        playlistId,
                        dateCreated,
                        userId
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error retrieving playlist: "
                            + e.getMessage()
            );
        }

        return null;
    }

    // ========================================
    // SEARCH PLAYLIST
    // ========================================

    @Override
    public List<Playlist> searchPlaylist(
            String keyword
    ) {

        List<Playlist> playlists =
                new ArrayList<>();

        String sql =
                "SELECT * FROM playlists " +
                        "WHERE date_created LIKE ?";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(
                    1,
                    "%" + keyword + "%"
            );

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                String dateCreated =
                        resultSet.getString("date_created");

                int userId =
                        resultSet.getInt("user_id");

                playlists.add(
                        new Playlist(
                                id,
                                dateCreated,
                                userId
                        )
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error searching playlists: "
                            + e.getMessage()
            );
        }

        return playlists;
    }

    // ========================================
    // CREATE PLAYLIST
    // ========================================

    @Override
    public boolean createPlaylist(
            String dateCreated,
            int userId
    ) {

        String sql =
                "INSERT INTO playlists " +
                        "(date_created, user_id) " +
                        "VALUES (?, ?)";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(
                    1,
                    dateCreated
            );

            preparedStatement.setInt(
                    2,
                    userId
            );

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error creating playlist: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // ========================================
    // UPDATE PLAYLIST
    // ========================================

    @Override
    public boolean updatePlaylist(
            String dateCreated,
            int userId,
            int id
    ) {

        String sql =
                "UPDATE playlists " +
                        "SET date_created = ?, user_id = ? " +
                        "WHERE id = ?";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(
                    1,
                    dateCreated
            );

            preparedStatement.setInt(
                    2,
                    userId
            );

            preparedStatement.setInt(
                    3,
                    id
            );

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error updating playlist: "
                            + e.getMessage()
            );
        }

        return false;
    }

    // ========================================
    // DELETE PLAYLIST
    // ========================================

    @Override
    public boolean deletePlaylist(int id) {

        String deletePlaylistSongsSql =
                "DELETE FROM playlist_songs WHERE playlist_id = ?";

        String deletePlaylistSql =
                "DELETE FROM playlists WHERE id = ?";

        try (
                Connection connection =
                        dbConnection.connect()
        ) {

            // Start transaction
            connection.setAutoCommit(false);

            try (
                    PreparedStatement deletePlaylistSongs =
                            connection.prepareStatement(
                                    deletePlaylistSongsSql
                            );

                    PreparedStatement deletePlaylist =
                            connection.prepareStatement(
                                    deletePlaylistSql
                            )
            ) {

                // ========================================
                // DELETE SONGS FROM PLAYLIST
                // ========================================

                deletePlaylistSongs.setInt(
                        1,
                        id
                );

                deletePlaylistSongs.executeUpdate();

                // ========================================
                // DELETE PLAYLIST
                // ========================================

                deletePlaylist.setInt(
                        1,
                        id
                );

                int rowsDeleted =
                        deletePlaylist.executeUpdate();

                // ========================================
                // COMMIT
                // ========================================

                if (rowsDeleted > 0) {

                    connection.commit();

                    return true;
                }

                // Nothing was deleted
                connection.rollback();

            } catch (Exception e) {

                connection.rollback();

                System.out.println(
                        "Error deleting playlist: "
                                + e.getMessage()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error connecting to database: "
                            + e.getMessage()
            );
        }

        return false;
    }
}