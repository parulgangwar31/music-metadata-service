package com.example.musicmetadata.repository;

import com.example.musicmetadata.dao.ArtistDao;
import com.example.musicmetadata.database.InMemoryDatabase;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArtistRepositoryTest {

    private final InMemoryDatabase database = mock(InMemoryDatabase.class);
    private final ArtistRepository repository = new ArtistRepository(database);

    @Test
    void updateArtistNameDelegatesToDatabase() {
        ArtistDao expected = new ArtistDao(7, "New Alias");
        when(database.updateArtistName(7, "New Alias")).thenReturn(expected);

        ArtistDao result = repository.updateArtistName(7, "New Alias");

        assertEquals(expected, result);
        verify(database).updateArtistName(7, "New Alias");
    }

    @Test
    void getArtistsDelegatesToDatabase() {
        List<ArtistDao> expected = List.of(new ArtistDao(1, "Artist"));
        when(database.getArtists()).thenReturn(expected);

        List<ArtistDao> result = repository.getArtists();

        assertEquals(expected, result);
        verify(database).getArtists();
    }
}
