package com.forum.forumhub.shared.exceptions.forums;

import com.forum.forumhub.shared.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class PermissionDeniedForumException extends ApiException {

    public PermissionDeniedForumException() {
        super(
                "You do not have permission to create this forum.",
                HttpStatus.FORBIDDEN,
                "FORUM_PERMISSION_DENIED"
        );
    }

    public PermissionDeniedForumException(String message) {
        super(message, HttpStatus.FORBIDDEN, "FORUM_PERMISSION_DENIED");
    }
}