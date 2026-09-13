package com.example.musicmetadata.repository;

import com.example.musicmetadata.dao.TrackDao;
import com.example.musicmetadata.database.InMemoryDatabase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackRepository {

    private final InMemoryDatabase database;

    public TrackRepository() {
        this(InMemoryDatabase.getInstance());
    }

    public TrackRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public TrackDao addTrack(long artistId, String title, String genre, int lengthInSeconds) {
        return database.addTrack(artistId, title, genre, lengthInSeconds);
    }

    public List<TrackDao> getArtistTracks(long artistId) {
        return database.getArtistTracks(artistId);
    }
}
