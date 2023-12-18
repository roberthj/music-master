package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.ticketmasterapiresponse.EventsRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Service
public class TicketMasterApiClientImpl implements TicketMasterApiClient {

  public static final String BASE_URI_TICKETMASTER = "http://app.ticketmaster.com/discovery/v2/";

  private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @Value("${ticketmaster.api.key}")
  private String apiKey;

  private final HttpWebClient httpWebClient;

    public TicketMasterApiClientImpl(HttpWebClient httpWebClient) {
        this.httpWebClient = httpWebClient;
    }

    //TODO: Put interface before this class




  public String findEventsForArtist(String artist) throws IOException {

    var uri = generateFullTicketmasterUri("/events.json", artist, apiKey);

    HttpHeaders headers = new HttpHeaders();

    var response = httpWebClient.getSyncronously(uri, headers);

   var responseObject = objectMapper.readValue(response, EventsRoot.class);

  //TODO: deal with pagination to get all events


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

