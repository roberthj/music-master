package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class SpotifyApiAuth {

  @Value("${spotify.api.client_id}")
  private String clientId;

  @Value("${spotify.api.client_secret}")
  private String clientSecret;

  //TODO: Remove comments
  public String getAccessToken()  {

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

      ObjectMapper objectMapper = new ObjectMapper(); //TODO: Move out

      try {
        var authResponse = objectMapper.readValue(responseBody, SpotifyApiAuthResponse.class);

        return authResponse.getAccessToken();

      } catch (JsonProcessingException e) {

        //TODO: Throw Exeption

        System.out.println("Error while parsing json response");

        return null;
      }


    } else {
      //TODO: Throw Exeption
      System.out.println("Error: " + responseEntity.getStatusCode());
      return null;
    }
  }
}
