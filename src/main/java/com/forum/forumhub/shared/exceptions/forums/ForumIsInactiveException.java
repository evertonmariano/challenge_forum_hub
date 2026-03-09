package com.forum.forumhub.shared.exceptions.forums;

public class ForumIsInactiveException extends RuntimeException {
    public ForumIsInactiveException(String message) {
        super(message);
    }
}
