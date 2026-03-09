package com.forum.forumhub.shared.utils.enums;

public enum ForumType {
    OPEN_DISCUSSION,     // FÃ³rum aberto geral
    OPEN_QUESTION,       // FÃ³rum de dÃºvidas aberto
    COURSE_GENERAL,      // FÃ³rum de curso geral
    COURSE_QA;

    public boolean isOpen() {
        return this.name().startsWith("OPEN");
    }

    public boolean isCourse() {
        return this.name().startsWith("COURSE");
    }

    public boolean isDiscussion() {
        return this == OPEN_DISCUSSION || this == COURSE_GENERAL;
    }

    public boolean isQuestion() {
        return this == OPEN_QUESTION || this == COURSE_QA;
    }

    public boolean supports(TopicType topicType) {
        return switch (this) {
            case OPEN_DISCUSSION, COURSE_GENERAL -> topicType == TopicType.DISCUSSION;
            case OPEN_QUESTION, COURSE_QA -> topicType == TopicType.DOUBT;
        };
    }
}
