package com.roberthj.musicmaster.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.client.SpotifyApiAuthResponse;
import com.roberthj.musicmaster.client.SpotifyApiClient;
import java.net.URI;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MusicMasterService {

  @Value("${spotify.api.client_id}")
  private String clientId;

  @Value("${spotify.api.client_secret}")
  private String clientSecret;

  public static final String BASE_URI = "https://api.spotify.com/v1";
  private final SpotifyApiClient spotifyApiClient;

  public MusicMasterService(final SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  public String lookupArtistId (String artist) throws JsonProcessingException {


    var tokenUrl =
        UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token")
            .build(true)
            .toUri();

    // Encode client_id and client_secret in Base64
    String credentials = clientId + ":" + clientSecret;

    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    // Build the request headers
    HttpHeaders authHeaders = new HttpHeaders();
    authHeaders.set("Authorization", "Basic " + encodedCredentials);
    authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // Build the request body
    String requestBody = "grant_type=client_credentials";

    var authTokenResponse = spotifyApiClient.postSyncronously(tokenUrl, authHeaders, requestBody);

    ObjectMapper objectMapper = new ObjectMapper(); //TODO: Move out

    var authResponse = objectMapper.readValue(authTokenResponse, SpotifyApiAuthResponse.class);

    var accessToken= authResponse.getAccessToken();

    var uri = generateFullSearchUri("/search","artist", artist); // Pass this in from service
    // instead

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Bearer " + accessToken);

    var artistResponse = spotifyApiClient.getSyncronously(uri, headers);

    System.out.println(artistResponse);

    //Use search endpoint, pass in name, pick the most popular one and return the id

    return "id";

  }

  private URI generateFullSearchUri(String path, String type, String value) {

    return UriComponentsBuilder.fromUriString(BASE_URI + path)
        .queryParam("type", type)
        .queryParam("q", value)
        .build(true)
        .toUri();
  }

}
