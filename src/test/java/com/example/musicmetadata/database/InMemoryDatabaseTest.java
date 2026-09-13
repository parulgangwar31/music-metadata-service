package com.example.musicmetadata.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryDatabaseTest {

    @Test
    void getInstanceReturnsTheSameDatabase() {
        assertSame(InMemoryDatabase.getInstance(), InMemoryDatabase.getInstance());
    }

    @Test
    void addTrackRejectsUnknownArtist() {
        assertThrows(
                IllegalArgumentException.class,
                () -> InMemoryDatabase.getInstance().addTrack(999999, "Song", "Rock", 210));
    }

    @Test
    void updateArtistNameRejectsUnknownArtist() {
        assertThrows(
                IllegalArgumentException.class,
                () -> InMemoryDatabase.getInstance().updateArtistName(999999, "New Alias"));
    }

    @Test
    void getArtistTracksRejectsUnknownArtist() {
        assertThrows(
                IllegalArgumentException.class,
                () -> InMemoryDatabase.getInstance().getArtistTracks(999999));
    }

    @Test
    void getArtistsReturnsSeededArtists() {
        assertTrue(InMemoryDatabase.getInstance().getArtists().size() > 0);
    }
}
