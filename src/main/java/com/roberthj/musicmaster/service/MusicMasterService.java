package com.roberthj.musicmaster.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roberthj.musicmaster.client.SpotifyApiClient;
import com.roberthj.musicmaster.client.TicketMasterApiClient;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MusicMasterService {

  @Value("${ticketmaster.api.key}")
  private String apiKey;

  public static final String BASE_URI_SPOTIFY = "https://api.spotify.com/v1";

  public static final String BASE_URI_TICKETMASTER = "http://app.ticketmaster.com/discovery/v2/";
  private final SpotifyApiClient spotifyApiClient;

  private final TicketMasterApiClient ticketMasterApiClient;

  public MusicMasterService(
      final SpotifyApiClient spotifyApiClient, final TicketMasterApiClient ticketMasterApiClient) {
    this.spotifyApiClient = spotifyApiClient;
    this.ticketMasterApiClient = ticketMasterApiClient;
  }

  public String lookupArtistId(String artist) throws JsonProcessingException {

    var uri = generateFullSearchUri("/search", "artist", artist);

    var artistResponse = spotifyApiClient.getSyncronously(uri);

    // Todo: Parse response and pick the most popular artist if more than one exists

    return artistResponse;
  }

  public String lookupEvent(String artist) {

    var ticketmasterUri = generateFullTicketmasterUri("/events.json", artist, apiKey);

    var eventResponse = ticketMasterApiClient.findEventsForArtist(ticketmasterUri);

    // Todo: Parse response and pick the most popular artist if more than one exists

    return eventResponse;
  }

  private URI generateFullSearchUri(String path, String type, String value) {

    return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY + path)
        .queryParam("type", type)
        .queryParam("q", value)
        .build(true)
        .toUri();
  }

  private URI generateFullTicketmasterUri(String path, String keyword, String apiKey) {

    return UriComponentsBuilder.fromUriString(BASE_URI_TICKETMASTER + path)
        .queryParam("keyword", keyword)
        .queryParam("apikey", apiKey)
        .build(true)
        .toUri();
  }
}
