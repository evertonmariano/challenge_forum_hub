package com.forum.forumhub.shared.exceptions.topics;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }
}
