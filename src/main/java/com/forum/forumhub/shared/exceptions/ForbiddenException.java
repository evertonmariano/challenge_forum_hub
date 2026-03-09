package com.forum.forumhub.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApiException {

    private static final String ERROR_CODE = "FORBIDDEN";

    public ForbiddenException() {
        super(
                "You do not have permission to access this resource.",
                HttpStatus.FORBIDDEN,
                ERROR_CODE
        );
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN, ERROR_CODE);
    }
}