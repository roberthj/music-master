package com.roberthj.musicmaster.client;

import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyApiClient {

  public static final String BASE_URI = "https://api.spotify.com/v1";
  private final SpotifyApiAuth spotifyApiAuth;

  public SpotifyApiClient(final SpotifyApiAuth spotifyApiAuth) {
    this.spotifyApiAuth = spotifyApiAuth;
  }

  public void findArtistByName() {
    var accessToken = spotifyApiAuth.getAccessToken();

    System.out.println(accessToken);

    var uri = generateFullUri("/search"); // Pass this in from service instead

    WebClient webClient = WebClient.create();
    // Create HttpHeaders object and set multiple headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Bearer " + accessToken);
    webClient
        .get()
        .uri(uri)
        .headers(h -> h.addAll(headers))
        .retrieve()
        .bodyToMono(String.class)
        .subscribe(response -> System.out.println("Response: " + response));
  }

  private URI generateFullUri(String path) {

    return UriComponentsBuilder.fromUriString(BASE_URI + path)
        .queryParam("type", "artist")
        .queryParam("q", "Metallica")
        .build(true)
        .toUri();
  }
}
