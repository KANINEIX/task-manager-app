package org.example.model.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        int errorCode,
        String errorMsg,
        LocalDateTime errorDateTime
) {

}
