package com.example.musicmetadata.exception;

public record ApiError(
        int status,
        String error,
        String message
) {
}
