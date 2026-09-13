package com.example.musicmetadata.responseBody;

public record TrackResponse(long id, long artistId, String title, String genre, int lengthInSeconds) {
}
