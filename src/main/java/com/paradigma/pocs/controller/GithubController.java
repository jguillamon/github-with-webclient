package com.paradigma.pocs.controller;

import com.paradigma.pocs.dto.tweet.User;
import com.paradigma.pocs.services.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class GithubController {

    private GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(value = "/user-info-github")
    public Mono<ResponseEntity> getUserLoggerInfoFromGithub(@RequestParam String code) {
        return githubService.login(code).flatMap(response -> {
            if (Objects.nonNull(response.getAccessToken())) {
                Mono<User> user = githubService.getUser(response.getAccessToken());
                return Mono.just(ResponseEntity.status(HttpStatus.OK).body(user));
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
            }
        });
    }
}
