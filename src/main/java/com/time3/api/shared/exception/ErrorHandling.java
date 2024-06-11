package com.time3.api.shared.exception;

import java.time.LocalDateTime;

public record ErrorHandling(LocalDateTime timestamps, Integer statusCode, String path, String method, String message) {
}