package com.example.musicmetadata.model;

public record Track(long id, long artistId, String title, String genre, int lengthInSeconds) {
}
