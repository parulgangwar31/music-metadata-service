package com.example.musicmetadata.database;

import com.example.musicmetadata.dao.ArtistDao;
import com.example.musicmetadata.dao.TrackDao;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class InMemoryDatabase {

    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();

    private final Map<Long, ArtistDao> artists = new ConcurrentHashMap<>();
    private final Map<Long, TrackDao> tracks = new ConcurrentHashMap<>();
    private final AtomicLong trackIdSequence = new AtomicLong();

    private InMemoryDatabase() {
    }

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    public TrackDao addTrack(long artistId, String title, String genre, int lengthInSeconds) {
        if (!artists.containsKey(artistId)) {
            throw new IllegalArgumentException("Artist does not exist: " + artistId);
        }

        long trackId = trackIdSequence.incrementAndGet();
        TrackDao track = new TrackDao(trackId, artistId, title, genre, lengthInSeconds);
        tracks.put(trackId, track);
        return track;
    }

    public ArtistDao updateArtistName(long artistId, String name) {
        ArtistDao artist = artists.get(artistId);
        if (artist == null) {
            throw new IllegalArgumentException("Artist does not exist: " + artistId);
        }

        ArtistDao updatedArtist = new ArtistDao(artist.id(), name);
        artists.put(artistId, updatedArtist);
        return updatedArtist;
    }

    public List<TrackDao> getArtistTracks(long artistId) {
        if (!artists.containsKey(artistId)) {
            throw new IllegalArgumentException("Artist does not exist: " + artistId);
        }

        return tracks.values().stream()
                .filter(track -> track.artistId() == artistId)
                .sorted((left, right) -> Long.compare(left.id(), right.id()))
                .toList();
    }

    public List<ArtistDao> getArtists() {
        return artists.values().stream()
                .sorted((left, right) -> Long.compare(left.id(), right.id()))
                .toList();
    }
}
