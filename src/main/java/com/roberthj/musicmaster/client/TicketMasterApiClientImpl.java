package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.Event;
import com.roberthj.musicmaster.models.ticketmasterapiresponse.EventTmResponse;
import com.roberthj.musicmaster.models.ticketmasterapiresponse.EventsRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketMasterApiClientImpl implements TicketMasterApiClient {

    public static final String BASE_URI_TICKETMASTER = "http://app.ticketmaster.com/discovery/v2/";

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private String apiKey;

    private final HttpWebClient httpWebClient;

    public TicketMasterApiClientImpl(HttpWebClient httpWebClient, @Value("${ticketmaster.api.key}") String apiKey) {
        this.httpWebClient = httpWebClient;
        this.apiKey = apiKey;
    }

    public List<Event> findEventsForArtist(String artist) {

        var uri = generateFullTicketmasterUri("/events.json", artist, apiKey);

        HttpHeaders headers = new HttpHeaders();

        var response = httpWebClient.getSyncronously(uri, headers);

        EventsRoot responseObject = null;
        try {
            responseObject = objectMapper.readValue(response, EventsRoot.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO: Make exception more specific?
        }

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

        return events.stream()
                .map(
                        item -> {

                            var event =
                                    Event.builder()
                                            .name(item.getName())
                                            .type(item.getType())
                                            .id(item.getId())
                                            .url(item.getUrl())
                                            .startDate(item.getDates().getStart().getLocalDate())
                                            .notes(item.getPleaseNote());
                            if (item.getEmbedded() != null) {
                                var venue = item.getEmbedded().getVenues().get(0);
                                event.venue(venue.getName())
                                        .address(venue.getAddress().getLine1())
                                        .city(venue.getCity().getName())
                                        .country(venue.getCountry().getName());


                            }
                            return event.build();

                        }).toList();

    }

}

