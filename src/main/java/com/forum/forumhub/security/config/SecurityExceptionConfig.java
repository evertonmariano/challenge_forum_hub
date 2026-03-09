package com.forum.forumhub.security.config;

import com.forum.forumhub.security.handlers.CustomAccessDeniedHandler;
import com.forum.forumhub.security.handlers.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityExceptionConfig {

    public SecurityExceptionConfig(
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler
    ) {
        // apenas garante criaÃ§Ã£o dos beans
    }
}