package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class SpotifyApiClient {

  //TODO: Add interface before this class
  @Value("${spotify.api.client_id}")
  private String clientId;

  @Value("${spotify.api.client_secret}")
  private String clientSecret;

  @Autowired
  private WebClient webClient;

  public String getSyncronously(URI uri) throws JsonProcessingException {

    var accessToken = getAccessToken();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Bearer " + accessToken);
    return webClient
        .get()
        .uri(uri)
        .headers(h -> h.addAll(headers))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    //TODO: What about error handling?
  }

  private String postSyncronously(URI uri, HttpHeaders headers, String body) {

    return webClient
        .post()
        .uri(uri)
        .headers(h -> h.addAll(headers))
        .body(Mono.just(body), String.class)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    //TODO: What about error handling?
    // some examples here https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
  }

  private String getAccessToken() throws JsonProcessingException {

    var tokenUrl =
        UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token")
            .build(true)
            .toUri();

    String credentials = clientId + ":" + clientSecret;

    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    HttpHeaders authHeaders = new HttpHeaders();
    authHeaders.set("Authorization", "Basic " + encodedCredentials);
    authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    String requestBody = "grant_type=client_credentials";

    var authTokenResponse = postSyncronously(tokenUrl, authHeaders, requestBody);

    ObjectMapper objectMapper = new ObjectMapper(); //TODO: Move out

    SpotifyApiAuthResponse
        authResponse = objectMapper.readValue(authTokenResponse, SpotifyApiAuthResponse.class);

    return authResponse.getAccessToken();
  }


}
