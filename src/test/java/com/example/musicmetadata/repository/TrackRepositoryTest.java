package com.example.musicmetadata.repository;

import com.example.musicmetadata.dao.TrackDao;
import com.example.musicmetadata.database.InMemoryDatabase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackRepositoryTest {

    private final InMemoryDatabase database = mock(InMemoryDatabase.class);
    private final TrackRepository repository = new TrackRepository(database);

    @Test
    void addTrackDelegatesToDatabase() {
        TrackDao expected = new TrackDao(1, 7, "Song", "Rock", 210);
        when(database.addTrack(7, "Song", "Rock", 210)).thenReturn(expected);

        TrackDao result = repository.addTrack(7, "Song", "Rock", 210);

        assertEquals(expected, result);
        verify(database).addTrack(7, "Song", "Rock", 210);
    }

    @Test
    void getArtistTracksDelegatesToDatabase() {
        List<TrackDao> expected = List.of(new TrackDao(1, 7, "Song", "Rock", 210));
        when(database.getArtistTracks(7)).thenReturn(expected);

        List<TrackDao> result = repository.getArtistTracks(7);

        assertEquals(expected, result);
        verify(database).getArtistTracks(7);
    }
}
