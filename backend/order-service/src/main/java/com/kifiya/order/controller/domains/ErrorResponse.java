package com.kifiya.order.controller.domains;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path
) {
}
