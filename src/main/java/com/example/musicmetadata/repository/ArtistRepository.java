package com.example.musicmetadata.repository;

import com.example.musicmetadata.dao.ArtistDao;
import com.example.musicmetadata.database.InMemoryDatabase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistRepository {

    private final InMemoryDatabase database;

    public ArtistRepository() {
        this(InMemoryDatabase.getInstance());
    }

    public ArtistRepository(InMemoryDatabase database) {
        this.database = database;
    }

    public ArtistDao updateArtistName(long artistId, String name) {
        return database.updateArtistName(artistId, name);
    }

    public List<ArtistDao> getArtists() {
        return database.getArtists();
    }

    public List<ArtistDao> getAllArtists() {
        return database.getArtists();
    }
}
