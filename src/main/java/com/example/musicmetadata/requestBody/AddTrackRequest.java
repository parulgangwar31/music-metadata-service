package com.example.musicmetadata.requestBody;

public record AddTrackRequest(String title, String genre, int lengthInSeconds) {
}
