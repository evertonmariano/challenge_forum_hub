package com.forum.forumhub.shared.exceptions.topics;

public class ClosedTopicException extends RuntimeException {
    public ClosedTopicException(String message) {
        super(message);
    }
}
