package com.roberthj.musicmaster.client;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyApiAuth {

  @Value("${spotify.api.client_id}")
  private String clientId;

  @Value("${spotify.api.client_secret}")
  private String clientSecret;

  //TODO: Remove comments
  public String getAccessToken() {

    // Encode client_id and client_secret in Base64
    String credentials = clientId + ":" + clientSecret;
    // String encodedCredentials = new String(Base64.encode(credentials.getBytes()));

    String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    // Spotify API token endpoint
    String tokenUrl = "https://accounts.spotify.com/api/token";

    // Build the request headers
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Basic " + encodedCredentials);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // Build the request body
    String requestBody = "grant_type=client_credentials";

    // Build the request entity
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Create a RestTemplate
    RestTemplate restTemplate = new RestTemplate();

    // Make the HTTP POST request
    ResponseEntity<String>
        responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, String.class);

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      // Parse the response JSON to get the access token
      String responseBody = responseEntity.getBody();
      // Handle the responseBody JSON accordingly
      return responseBody;
    } else {
      //TODO: Throw Exeption
      System.out.println("Error: " + responseEntity.getStatusCode());
      return null;
    }
  }
}
