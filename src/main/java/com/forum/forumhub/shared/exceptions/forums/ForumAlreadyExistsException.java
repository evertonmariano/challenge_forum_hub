package com.forum.forumhub.shared.exceptions.forums;

import com.forum.forumhub.shared.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class ForumAlreadyExistsException extends ApiException {

    public ForumAlreadyExistsException() {
        super(
                "You already have this type of forum.",
                HttpStatus.BAD_REQUEST,
                "FORUM_ALREADY_EXISTS"
        );
    }

    public ForumAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "FORUM_ALREADY_EXISTS");
    }
}