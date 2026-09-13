package com.example.musicmetadata.controller;

import com.example.musicmetadata.requestBody.AddTrackRequest;
import com.example.musicmetadata.responseBody.ArtistResponse;
import com.example.musicmetadata.responseBody.TrackResponse;
import com.example.musicmetadata.requestBody.UpdateArtistNameRequest;
import com.example.musicmetadata.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArtistControllerTest {

    private final ArtistService artistService = mock(ArtistService.class);
    private final ArtistController controller = new ArtistController(artistService);

    @Test
    void addTrackReturnsCreatedTrack() {
        AddTrackRequest request = new AddTrackRequest("Song", "Rock", 210);
        TrackResponse expected = new TrackResponse(1, 7, "Song", "Rock", 210);
        when(artistService.artistExists(7)).thenReturn(true);
        when(artistService.addTrack(7, "Song", "Rock", 210)).thenReturn(expected);

        ResponseEntity<TrackResponse> response = controller.addTrack(7, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
        verify(artistService).addTrack(7, "Song", "Rock", 210);
    }

    @Test
    void updateArtistNameReturnsUpdatedArtist() {
        UpdateArtistNameRequest request = new UpdateArtistNameRequest("New Alias");
        ArtistResponse expected = new ArtistResponse(7, "New Alias");
        when(artistService.artistExists(7)).thenReturn(true);
        when(artistService.updateArtistName(7, "New Alias")).thenReturn(expected);

        ArtistResponse response = controller.updateArtistName(7, request);

        assertEquals(expected, response);
        verify(artistService).updateArtistName(7, "New Alias");
    }

    @Test
    void getArtistTracksReturnsTracksForArtist() {
        List<TrackResponse> expected = List.of(new TrackResponse(1, 7, "Song", "Rock", 210));
        when(artistService.artistExists(7)).thenReturn(true);
        when(artistService.getArtistTracks(7)).thenReturn(expected);

        List<TrackResponse> response = controller.getArtistTracks(7);

        assertEquals(expected, response);
        verify(artistService).getArtistTracks(7);
    }

    @Test
    void getArtistOfTheDayReturnsArtist() {
        ArtistResponse expected = new ArtistResponse(7, "Artist");
        when(artistService.getAllArtists()).thenReturn(List.of(expected));
        when(artistService.getArtistOfTheDay()).thenReturn(expected);

        ArtistResponse response = controller.getArtistOfTheDay();

        assertEquals(expected, response);
        verify(artistService).getArtistOfTheDay();
    }
}
