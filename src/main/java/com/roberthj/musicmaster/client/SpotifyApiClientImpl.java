package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.artistapiresponse.Root;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyApiClientImpl implements SpotifyApiClient {

  public static final String BASE_URI_SPOTIFY = "https://api.spotify.com/v1";

  private final ObjectMapper objectMapper = new ObjectMapper();

  //TODO: Add interface before this class
  @Value("${spotify.api.client_id}")
  private String clientId;

  @Value("${spotify.api.client_secret}")
  private String clientSecret;

  private final HttpWebClient httpWebClient;

    public SpotifyApiClientImpl(HttpWebClient httpWebClient) {
        this.httpWebClient = httpWebClient;
    }

    public List<Artist> getArtistByName(String artist) throws JsonProcessingException {

    var accessToken = getAccessToken();

    var uri = generateFullSearchUri("/search", "artist", artist);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Bearer " + accessToken);

    var response = httpWebClient.getSyncronously(uri, headers);

    var responseObject = objectMapper.readValue(response, Root.class);

    return extractArtist(responseObject);

  }

  private URI generateFullSearchUri(String path, String type, String value) {

    return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY + path)
            .queryParam("type", type)
            .queryParam("q", value)
            .build(true)
            .toUri();
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

    var authTokenResponse = httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody);

    SpotifyApiAuthResponse
        authResponse = objectMapper.readValue(authTokenResponse, SpotifyApiAuthResponse.class);

    return authResponse.getAccessToken();
  }

  private List<Artist> extractArtist(Root responseObject) {

      var items = responseObject.artists.getItems();

    return items.stream()
        .map(
            item -> {
              var artist = new Artist();
              artist.setExternalUrls(item.getExternalUrls());
              artist.setFollowers(item.getFollowers());
              artist.setGenres(item.getGenres());
              artist.setHref(item.getHref());
              artist.setId(item.getId());
              artist.setImages(item.getImages());
              artist.setName(item.getName());
              artist.setPopularity(item.getPopularity());
              artist.setType(item.getType());
              artist.setUri(item.getUri());

              return artist;
            }).toList();

  }


}
