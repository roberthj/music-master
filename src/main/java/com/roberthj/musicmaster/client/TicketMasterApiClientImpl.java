package com.roberthj.musicmaster.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TicketMasterApiClientImpl implements TicketMasterApiClient {

  public static final String BASE_URI_TICKETMASTER = "http://app.ticketmaster.com/discovery/v2/";

  @Value("${ticketmaster.api.key}")
  private String apiKey;

  private final HttpWebClient httpWebClient;

    public TicketMasterApiClientImpl(HttpWebClient httpWebClient) {
        this.httpWebClient = httpWebClient;
    }

    //TODO: Put interface before this class



  public String findEventsForArtist(String artist) {

    var uri = generateFullTicketmasterUri("/events.json", artist, apiKey);

    HttpHeaders headers = new HttpHeaders();

    return httpWebClient.getSyncronously(uri, headers);

  }

  private URI generateFullTicketmasterUri(String path, String keyword, String apiKey) {

    return UriComponentsBuilder.fromUriString(BASE_URI_TICKETMASTER + path)
            .queryParam("keyword", keyword)
            .queryParam("apikey", apiKey)
            .build(true)
            .toUri();

  }

  }

