package com.example.musicmetadata.controller;

import com.example.musicmetadata.service.ArtistService;
import com.example.musicmetadata.requestBody.AddTrackRequest;
import com.example.musicmetadata.responseBody.ArtistResponse;
import com.example.musicmetadata.responseBody.TrackResponse;
import com.example.musicmetadata.requestBody.UpdateArtistNameRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // Assumption: artist creation and initial catalogue loading are handled upstream.

    @PostMapping("/artists/{artistId}/tracks")
    public ResponseEntity<TrackResponse> addTrack(
            @PathVariable long artistId,
            @RequestBody AddTrackRequest request) {
        TrackResponse response = artistService.addTrack(
                artistId,
                request.title(),
                request.genre(),
                request.lengthInSeconds());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/artists/{artistId}")
    public ArtistResponse updateArtistName(
            @PathVariable long artistId,
            @RequestBody UpdateArtistNameRequest request) {
        return artistService.updateArtistName(artistId, request.name());
    }

    @GetMapping("/artists/{artistId}/tracks")
    public List<TrackResponse> getArtistTracks(@PathVariable long artistId) {
        return artistService.getArtistTracks(artistId);
    }

    @GetMapping("/artists/artist-of-the-day")
    public ArtistResponse getArtistOfTheDay() {
        return artistService.getArtistOfTheDay();
    }
}
