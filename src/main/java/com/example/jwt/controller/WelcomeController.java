package com.example.jwt.controller;

import com.example.jwt.dto.AuthRequestDTO;
import com.example.jwt.model.UserEntity;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.JsonService;
import com.example.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/")
public class WelcomeController {

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private JsonService jsonService;
    private UserRepository userRepository;

    @Autowired
    public WelcomeController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, JsonService jsonService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.jsonService = jsonService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Username/Password");
        }

        return jsonService.createJson("token", jwtUtil.generateToken(authRequestDTO.getUsername()));
    }
}
