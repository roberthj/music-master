package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.spotifyapiresponse.ArtistsRoot;
import com.roberthj.musicmaster.models.spotifyapiresponse.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

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

        HttpHeaders headers = getHttpHeaders(accessToken);

        var response = httpWebClient.getSyncronously(uri, headers);

    var responseObject = objectMapper.readValue(response, ArtistsRoot.class);

    return extractArtist(responseObject.getArtists().getItems());

  }


    public List<Artist> getRelatedArtists(String id) throws JsonProcessingException {
        var accessToken = getAccessToken();

        var uri = generateRelatedArtistsUri(id, "/related-artists");

        HttpHeaders headers = getHttpHeaders(accessToken);

        var response = httpWebClient.getSyncronously(uri, headers);

        var responseObject = objectMapper.readValue(response, Item.RelatedArtistsRoot.class);


        var uu =  extractArtist(responseObject.getArtists());

        return uu;
    }

    private URI generateFullSearchUri(String path, String type, String value) {

    return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY + path)
            .queryParam("type", type)
            .queryParam("q", URLEncoder.encode(value))
            .build(true)
            .toUri();
  }

    private URI generateRelatedArtistsUri(String id, String path) {

        return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY +"/artists/" +id + path)
                .build(true)
                .toUri();
    }

    private static HttpHeaders getHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
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

  private List<Artist> extractArtist(List<Item> items) {

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
            })
            .limit(5) //Due to rate limiting in TM api
            //TODO: create a better solution for this rate limiting
            .toList();

  }


}
