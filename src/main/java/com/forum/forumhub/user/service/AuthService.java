package com.forum.forumhub.user.service;

import com.forum.forumhub.security.jwt.JwtTokenProvider;
import com.forum.forumhub.security.principal.UserPrincipal;
import com.forum.forumhub.shared.exceptions.account.AccountDisabledException;
import com.forum.forumhub.shared.exceptions.account.InvalidCredentialsException;
import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.user.dto.auth.LoginDTO;
import com.forum.forumhub.user.dto.auth.LoginResponseDTO;
import com.forum.forumhub.user.dto.auth.RegisterDTO;
import com.forum.forumhub.user.dto.user.UserResponseDTO;
import com.forum.forumhub.user.entity.UserModel;
import com.forum.forumhub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository authReporitory;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtToken;

    public AuthService(
            UserRepository reporitory,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtToken
    ){
        this.authReporitory = reporitory;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
    }

    @Transactional
    public ResponseMessage registerUser(RegisterDTO dataUser) {

        if (authReporitory.findByEmail(dataUser.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        UserModel user = new UserModel(
                dataUser.name(),
                dataUser.email(),
                dataUser.phone(),
                passwordEncoder.encode(dataUser.password())
        );

        authReporitory.save(user);

        return new ResponseMessage("User successfully registered");
    }

    public LoginResponseDTO loginUser(LoginDTO dataUser) {

        try {

            var authToken = new UsernamePasswordAuthenticationToken(
                    dataUser.email(),
                    dataUser.password()
            );

            Authentication authentication =
                    authenticationManager.authenticate(authToken);

            UserPrincipal userPrincipal =
                    (UserPrincipal) authentication.getPrincipal();

            UserModel user = userPrincipal.getUser();

            String token = jwtToken.generateToken(user);

            UserResponseDTO userResponse = new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole().name()
            );

            return new LoginResponseDTO(token, userResponse);

        } catch (DisabledException ex) {
            throw new AccountDisabledException();
        }
        catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
