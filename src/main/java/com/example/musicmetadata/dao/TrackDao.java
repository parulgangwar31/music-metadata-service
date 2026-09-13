package com.example.musicmetadata.dao;

public record TrackDao(long id, long artistId, String title, String genre, int lengthInSeconds) {
}
