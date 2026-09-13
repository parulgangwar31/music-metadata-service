package com.example.musicmetadata.service;

import com.example.musicmetadata.responseBody.ArtistResponse;
import com.example.musicmetadata.responseBody.TrackResponse;
import com.example.musicmetadata.dao.ArtistDao;
import com.example.musicmetadata.dao.TrackDao;
import com.example.musicmetadata.model.Artist;
import com.example.musicmetadata.model.Track;
import com.example.musicmetadata.repository.ArtistRepository;
import com.example.musicmetadata.repository.TrackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;

    public ArtistService(ArtistRepository artistRepository, TrackRepository trackRepository) {
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
    }

    public TrackResponse addTrack(long artistId, String title, String genre, int lengthInSeconds) {
        Track track = toTrack(trackRepository.addTrack(artistId, title, genre, lengthInSeconds));
        return toTrackResponse(track);
    }

    public ArtistResponse updateArtistName(long artistId, String name) {
        Artist artist = toArtist(artistRepository.updateArtistName(artistId, name));
        return toArtistResponse(artist);
    }

    public List<TrackResponse> getArtistTracks(long artistId) {
        return trackRepository.getArtistTracks(artistId).stream()
                .map(this::toTrack)
                .map(this::toTrackResponse)
                .toList();
    }

    public boolean artistExists(long artistId) {
        return artistRepository.getArtists().stream()
                .anyMatch(artist -> artist.id() == artistId);
    }

    public ArtistResponse getArtistOfTheDay() {
        List<ArtistDao> artists = artistRepository.getArtists();
        if (artists.isEmpty()) {
            throw new IllegalStateException("No artists exist");
        }

        int artistIndex = (int) Math.floorMod(
                LocalDate.now(ZoneOffset.UTC).toEpochDay(),
                artists.size());
        return toArtistResponse(toArtist(artists.get(artistIndex)));
    }

    //get method for my local testing
    public List<ArtistResponse> getAllArtists() {
        return artistRepository.getAllArtists().stream()
                .map(this::toArtist)
                .map(this::toArtistResponse)
                .toList();
    }

    private Artist toArtist(ArtistDao artist) {
        return new Artist(artist.id(), artist.name());
    }

    private Track toTrack(TrackDao track) {
        return new Track(track.id(), track.artistId(), track.title(), track.genre(), track.lengthInSeconds());
    }

    private ArtistResponse toArtistResponse(Artist artist) {
        return new ArtistResponse(artist.id(), artist.name());
    }

    private TrackResponse toTrackResponse(Track track) {
        return new TrackResponse(track.id(), track.artistId(), track.title(), track.genre(), track.lengthInSeconds());
    }
}
