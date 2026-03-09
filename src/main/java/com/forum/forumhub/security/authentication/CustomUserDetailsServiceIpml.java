package com.forum.forumhub.security.authentication;

import com.forum.forumhub.security.principal.UserPrincipal;
import com.forum.forumhub.user.entity.UserModel;
import com.forum.forumhub.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceIpml implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsServiceIpml(
            UserRepository repository
    ){

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        UserModel user = repository.findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
            );

        return new UserPrincipal(user);
    }
}
