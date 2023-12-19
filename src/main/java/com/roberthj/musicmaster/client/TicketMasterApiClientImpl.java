package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.Event;
import com.roberthj.musicmaster.models.ticketmasterapiresponse.EventTmResponse;
import com.roberthj.musicmaster.models.ticketmasterapiresponse.EventsRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

    public List<Event> findEventsForArtist(String artist) throws IOException {

        var uri = generateFullTicketmasterUri("/events.json", artist, apiKey);

        HttpHeaders headers = new HttpHeaders();

        var response = httpWebClient.getSyncronously(uri, headers);

        var responseObject = objectMapper.readValue(response, EventsRoot.class);

        //TODO: deal with pagination to get all events

        if (responseObject.getEmbedded() == null) {
            return null;
        }
        return extractEvent(responseObject.getEmbedded().getEvents());
    }

    private URI generateFullTicketmasterUri(String path, String keyword, String apiKey) {

        return UriComponentsBuilder.fromUriString(BASE_URI_TICKETMASTER + path)
                .queryParam("keyword", URLEncoder.encode(keyword))
                .queryParam("apikey", apiKey)
                .build(true)
                .toUri();

    }

    private List<Event> extractEvent(ArrayList<EventTmResponse> events) {
        //TODO: change to builder?

        return events.stream()
                .map(
                        item -> {

                            var event = new Event();
                            event.setName(item.getName());
                            event.setType(item.getType());
                            event.setId(item.getId());
                            event.setUrl(item.getUrl());
                            event.setStartDate(item.getDates().getStart().getDateTime());
                            event.setNotes(item.getPleaseNote());

                            if (item.getEmbedded() != null) {
                                var venue = item.getEmbedded().getVenues().get(0);

                                event.setVenue(venue.getName());
                                event.setAddress(venue.getAddress().getLine1());
                                event.setCity(venue.getCity().getName());
                                event.setCountry(venue.getCountry().getName());
                            }


                            return event;
                        }).toList();

    }

}

