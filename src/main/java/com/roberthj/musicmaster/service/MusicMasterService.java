package com.roberthj.musicmaster.service;

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

  public String lookupArtistId () {

    var uri = generateFullSearchUri("/search","artist", "Metallica"); // Pass this in from service
    // instead

    var artist = spotifyApiClient.getSyncronously(uri);

    System.out.println(artist);

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
