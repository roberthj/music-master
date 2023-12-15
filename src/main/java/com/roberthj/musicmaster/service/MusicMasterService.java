package com.roberthj.musicmaster.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roberthj.musicmaster.client.SpotifyApiClient;
import java.net.URI;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MusicMasterService {

  public static final String BASE_URI = "https://api.spotify.com/v1";
  private final SpotifyApiClient spotifyApiClient;

  public MusicMasterService(final SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  public String lookupArtistId (String artist) throws JsonProcessingException {

    var uri = generateFullSearchUri("/search","artist", artist);

    var artistResponse = spotifyApiClient.getSyncronously(uri);

    //Todo: Parse response and pick the most popular artist if more than one exists

    return artistResponse;

  }

  private URI generateFullSearchUri(String path, String type, String value) {

    return UriComponentsBuilder.fromUriString(BASE_URI + path)
        .queryParam("type", type)
        .queryParam("q", value)
        .build(true)
        .toUri();
  }

}
