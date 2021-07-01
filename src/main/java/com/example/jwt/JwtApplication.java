package com.example.jwt;

import com.example.jwt.model.UserEntity;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void initUsers() {
        List<UserEntity> users = Stream.of(
                new UserEntity(Long.parseLong("1"), "admin", encoder.encode("admin"), "admin@gmail.com"),
                new UserEntity(Long.parseLong("2"), "user1", encoder.encode("user1"), "user1@gmail.com"),
                new UserEntity(Long.parseLong("3"), "user2", encoder.encode("user2"), "user2@gmail..com")
        ).collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }

}
