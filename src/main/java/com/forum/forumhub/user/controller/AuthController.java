package com.forum.forumhub.user.controller;

import com.forum.forumhub.shared.responses.ResponseMessage;
import com.forum.forumhub.user.dto.auth.LoginDTO;
import com.forum.forumhub.user.dto.auth.LoginResponseDTO;
import com.forum.forumhub.user.dto.auth.RegisterDTO;
import com.forum.forumhub.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service){
        this.service = service;
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(
            @RequestBody @Valid RegisterDTO dataUser
    ){
        var res = service.registerUser(dataUser);

        return  ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>loginUser(
            @RequestBody @Valid LoginDTO dataUser
    ){
        LoginResponseDTO response = service.loginUser(dataUser);

        return ResponseEntity.ok(response);
    }

}
