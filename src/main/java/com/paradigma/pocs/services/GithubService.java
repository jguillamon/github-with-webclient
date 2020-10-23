package com.paradigma.pocs.services;

import com.paradigma.pocs.dto.tweet.OauthData;
import com.paradigma.pocs.dto.tweet.User;
import com.paradigma.pocs.utils.WebClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class GithubService {

    final private static Logger log = LoggerFactory.getLogger(GithubService.class);

    final private static String URL_GITHUB_OAUTH_SERVER = "https://github.com/login/oauth";

    final private static String URL_GITHUB_API = "https://api.github.com";

    private String clientIdGithub;

    private String clientSecretGithub;


    public GithubService(@Value("${github.clientId}") String clientId,
                         @Value("${github.clientSecret}") String clientSecret) {
        this.clientIdGithub = clientId;
        this.clientSecretGithub = clientSecret;
    }


    public Mono<OauthData> login(String code) {
        return WebClient.builder().filter(WebClientUtil.errorHandlingFilterOauth()) //se registra un manejador de excepciones
                .baseUrl(URL_GITHUB_OAUTH_SERVER).build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/access_token")
                        .queryParam("client_id", "{client_id}")
                        .queryParam("client_secret", "{client_secret}")
                        .queryParam("code", "{code}")
                        .build(clientIdGithub, clientSecretGithub, code))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OauthData.class);

    }

    public Mono<User> getUser(String access_token) {
        return WebClient
                .create(URL_GITHUB_API)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user")
                        .build())
                .header("Authorization", "token " + access_token)
                .retrieve()
                .bodyToMono(User.class);
    }
}
