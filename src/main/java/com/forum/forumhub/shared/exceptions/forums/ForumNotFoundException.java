package com.forum.forumhub.shared.exceptions.forums;

public class ForumNotFoundException extends RuntimeException {

    public ForumNotFoundException(Long forumId) {
        super("Forum with id " + forumId + " was not found.");
    }

    public ForumNotFoundException(String message) {
        super(message);
    }
}