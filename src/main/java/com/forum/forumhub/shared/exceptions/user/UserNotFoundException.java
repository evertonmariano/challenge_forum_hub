package com.forum.forumhub.shared.exceptions.user;

import com.forum.forumhub.shared.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {
        super(
                "User not found",
                HttpStatus.NOT_FOUND,
                "USER_NOT_FOUND"
        );
    }
}