package com.bkravets.homework19.controller;

import com.bkravets.homework19.config.TokenProvider;
import com.bkravets.homework19.dto.LoginDto;
import com.bkravets.homework19.dto.UserDto;
import com.bkravets.homework19.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {

          String email = userService.create(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(email);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        String token = tokenProvider.createToken(authentication.getName());

        return ResponseEntity.ok(token);
    }
}
