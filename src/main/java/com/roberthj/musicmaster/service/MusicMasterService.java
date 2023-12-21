package com.roberthj.musicmaster.service;

import com.roberthj.musicmaster.client.SpotifyApiClient;
import com.roberthj.musicmaster.client.TicketMasterApiClient;
import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.Event;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicMasterService {

    private final SpotifyApiClient spotifyApiClient;

    private final TicketMasterApiClient ticketMasterApiClient;

    public MusicMasterService(
            final SpotifyApiClient spotifyApiClient, final TicketMasterApiClient ticketMasterApiClient) {
        this.spotifyApiClient = spotifyApiClient;
        this.ticketMasterApiClient = ticketMasterApiClient;
    }

    public Artist findEventByArtistName(String artist) {

        var artistResponse = spotifyApiClient.getArtistByName(artist);

        var mostPopularArtistOptional = artistResponse
                .stream()
                .filter(item -> item.getName().equalsIgnoreCase(artist))
                .max(Comparator.comparing(Artist::getPopularity));


        if (mostPopularArtistOptional.isEmpty()) {
           throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Artist " + artist + " not found");
        }

        var mostPopularArtist = mostPopularArtistOptional.get();
        mostPopularArtist.setEvents(getEventsForArtist(mostPopularArtist.getName()));

        var relatedArtists = spotifyApiClient.getRelatedArtists(mostPopularArtist.getId());

        //Add events for related artists
        relatedArtists
                .stream()
                .forEach(relatedArtist -> {
                    try {
                        Thread.sleep(200);  //TODO: Make more sophisticated. API can only take 5 requests per second
                        relatedArtist.setEvents(getEventsForArtist(relatedArtist.getName()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        //TODO: Can this be done in the step above already?
        var relatedArtistsWithEvents = relatedArtists.stream()
                .filter(a -> a.getEvents() != null)
                .collect(Collectors.toList());

        mostPopularArtist.setRelatedArtists(relatedArtistsWithEvents);

        //TODO: add city parameter as well

        return mostPopularArtist;

    }

    private List<Event> getEventsForArtist(String artistName) {
        return ticketMasterApiClient.findEventsForArtist(artistName);
    }

}
