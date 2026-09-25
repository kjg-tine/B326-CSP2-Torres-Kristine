package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Song;
import java.util.List;

public interface SongRepo {

    List<Song> getAllSongs();

    Song readSongById(int id);

    List<Song> searchSong(String keyword);

    boolean createSong(String title, String genre, int albumId);

    boolean updateSong(String title, String genre, int albumId, int id);

    boolean deleteSong(int id);
}