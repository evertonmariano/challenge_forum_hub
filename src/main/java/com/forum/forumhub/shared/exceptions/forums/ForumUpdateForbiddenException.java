package com.forum.forumhub.shared.exceptions.forums;

public class ForumUpdateForbiddenException extends RuntimeException {

    public ForumUpdateForbiddenException() {
        super("You do not have permission to update this forum.");
    }

    public ForumUpdateForbiddenException(String message) {
        super(message);
    }
}