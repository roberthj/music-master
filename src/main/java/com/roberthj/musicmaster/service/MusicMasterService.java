package com.roberthj.musicmaster.service;

import com.roberthj.musicmaster.client.SpotifyApiClient;
import org.springframework.stereotype.Service;

@Service
public class MusicMasterService {

  private final SpotifyApiClient spotifyApiClient;

  public MusicMasterService(final SpotifyApiClient spotifyApiClient) {
    this.spotifyApiClient = spotifyApiClient;
  }

  public String lookupArtistId () {

    spotifyApiClient.findArtistByName();

    //Use search endpoint, pass in name, pick the most popular one and return the id

    return "id";

  }
}
