package com.roberthj.musicmaster.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SpotifyApiClient {
  private final SpotifyApiAuth spotifyApiAuth;

  @Autowired
  private WebClient webClient;

  public SpotifyApiClient(final SpotifyApiAuth spotifyApiAuth) {
    this.spotifyApiAuth = spotifyApiAuth;
  }

  public String getSyncronously(URI uri) {
    var accessToken = spotifyApiAuth.getAccessToken();

    // Create HttpHeaders object and set multiple headers
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


}
