package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class TicketMasterApiClientImpl implements TicketMasterApiClient {

  public static final String BASE_URI_TICKETMASTER = "http://app.ticketmaster.com/discovery/v2/";

  private final ObjectMapper objectMapper = new ObjectMapper();

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

    var response = httpWebClient.getSyncronously(uri, headers);

   //var responseObject = objectMapper.readValues(response, EventsRoot.class);

    return "";
  }

  private URI generateFullTicketmasterUri(String path, String keyword, String apiKey) {

    return UriComponentsBuilder.fromUriString(BASE_URI_TICKETMASTER + path)
            .queryParam("keyword", keyword)
            .queryParam("apikey", apiKey)
            .build(true)
            .toUri();

  }

  }

