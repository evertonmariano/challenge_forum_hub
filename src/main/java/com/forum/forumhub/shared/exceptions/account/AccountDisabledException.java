package com.forum.forumhub.shared.exceptions.account;

import com.forum.forumhub.shared.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class AccountDisabledException extends ApiException {

    public AccountDisabledException() {
        super(
                "Your account has been deactivated. Please request account reactivation.",
                HttpStatus.FORBIDDEN,
                "ACCOUNT_DISABLED"
        );
    }
}