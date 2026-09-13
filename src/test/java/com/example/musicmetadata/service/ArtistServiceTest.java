package com.example.musicmetadata.service;

import com.example.musicmetadata.responseBody.TrackResponse;
import com.example.musicmetadata.responseBody.ArtistResponse;
import com.example.musicmetadata.dao.ArtistDao;
import com.example.musicmetadata.dao.TrackDao;
import com.example.musicmetadata.repository.ArtistRepository;
import com.example.musicmetadata.repository.TrackRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private final ArtistRepository artistRepository = mock(ArtistRepository.class);
    private final TrackRepository trackRepository = mock(TrackRepository.class);
    private final ArtistService service = new ArtistService(artistRepository, trackRepository);

    @Test
    void addTrackMapsRepositoryDaoToResponse() {
        TrackDao track = new TrackDao(1, 7, "Song", "Rock", 210);
        when(trackRepository.addTrack(7, "Song", "Rock", 210)).thenReturn(track);

        TrackResponse response = service.addTrack(7, "Song", "Rock", 210);

        assertEquals(new TrackResponse(1, 7, "Song", "Rock", 210), response);
        verify(trackRepository).addTrack(7, "Song", "Rock", 210);
    }

    @Test
    void updateArtistNameMapsRepositoryDaoToResponse() {
        ArtistDao artist = new ArtistDao(7, "New Alias");
        when(artistRepository.updateArtistName(7, "New Alias")).thenReturn(artist);

        ArtistResponse response = service.updateArtistName(7, "New Alias");

        assertEquals(new ArtistResponse(7, "New Alias"), response);
        verify(artistRepository).updateArtistName(7, "New Alias");
    }

    @Test
    void getArtistTracksMapsRepositoryDaosToResponses() {
        List<TrackDao> tracks = List.of(new TrackDao(1, 7, "Song", "Rock", 210));
        when(trackRepository.getArtistTracks(7)).thenReturn(tracks);

        List<TrackResponse> response = service.getArtistTracks(7);

        assertEquals(List.of(new TrackResponse(1, 7, "Song", "Rock", 210)), response);
        verify(trackRepository).getArtistTracks(7);
    }

    @Test
    void getArtistOfTheDaySelectsArtistFromDailyRotation() {
        List<ArtistDao> artists = List.of(
                new ArtistDao(1, "First"),
                new ArtistDao(2, "Second"),
                new ArtistDao(3, "Third"));
        when(artistRepository.getArtists()).thenReturn(artists);

        int expectedIndex = (int) Math.floorMod(
                LocalDate.now(ZoneOffset.UTC).toEpochDay(),
                artists.size());
        ArtistDao expectedArtist = artists.get(expectedIndex);

        ArtistResponse response = service.getArtistOfTheDay();

        assertEquals(new ArtistResponse(expectedArtist.id(), expectedArtist.name()), response);
        verify(artistRepository).getArtists();
    }

    @Test
    void getArtistOfTheDayRejectsEmptyCatalogue() {
        when(artistRepository.getArtists()).thenReturn(List.of());

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                service::getArtistOfTheDay);
    }
}
