package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistSongRepoImpl implements PlaylistSongRepo {

    private final DbConnection dbConnection;

    public PlaylistSongRepoImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {

        List<PlaylistSong> playlistSongs = new ArrayList<>();

        String query = "SELECT * FROM playlist_songs";

        try (Connection conn = dbConnection.connect();
             Statement stmnt = conn.createStatement();
             ResultSet result = stmnt.executeQuery(query)) {

            while (result.next()) {

                int id = result.getInt("id");
                int playlistId = result.getInt("playlist_id");
                int songId = result.getInt("song_id");

                playlistSongs.add(
                        new PlaylistSong(
                                id,
                                playlistId,
                                songId
                        )
                );
            }

        } catch (SQLException e) {
            System.err.println(
                    "Get All Playlist Songs: " + e.getMessage()
            );
        }

        return playlistSongs;
    }

    @Override
    public List<PlaylistSong> getSongsByPlaylistId(int playlistId) {

        List<PlaylistSong> playlistSongs = new ArrayList<>();

        String query =
                "SELECT * FROM playlist_songs WHERE playlist_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);

            try (ResultSet result = prep.executeQuery()) {

                while (result.next()) {

                    playlistSongs.add(
                            new PlaylistSong(
                                    result.getInt("id"),
                                    result.getInt("playlist_id"),
                                    result.getInt("song_id")
                            )
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Get Songs By Playlist ID: " + e.getMessage()
            );
        }

        return playlistSongs;
    }

    @Override
    public boolean addSongToPlaylist(
            int playlistId,
            int songId
    ) {

        String checkSql =
                "SELECT id FROM playlist_songs " +
                        "WHERE playlist_id = ? AND song_id = ?";

        String insertSql =
                "INSERT INTO playlist_songs " +
                        "(playlist_id, song_id) " +
                        "VALUES (?, ?)";

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement checkStatement =
                        connection.prepareStatement(
                                checkSql
                        )
        ) {

            // ========================================
            // CHECK IF ALREADY EXISTS
            // ========================================

            checkStatement.setInt(
                    1,
                    playlistId
            );

            checkStatement.setInt(
                    2,
                    songId
            );

            ResultSet resultSet =
                    checkStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println(
                        "Song is already in this playlist."
                );

                return false;
            }

            // ========================================
            // ADD SONG
            // ========================================

            try (
                    PreparedStatement insertStatement =
                            connection.prepareStatement(
                                    insertSql
                            )
            ) {

                insertStatement.setInt(
                        1,
                        playlistId
                );

                insertStatement.setInt(
                        2,
                        songId
                );

                return insertStatement.executeUpdate() > 0;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error adding song to playlist: "
                            + e.getMessage()
            );
        }

        return false;
    }

    @Override
    public boolean removeSongFromPlaylist(
            int playlistId,
            int songId
    ) {

        String query =
                "DELETE FROM playlist_songs " +
                        "WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, playlistId);
            prep.setInt(2, songId);

            return prep.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Remove Song From Playlist: " + e.getMessage()
            );
            return false;
        }
    }
}