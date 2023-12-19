package com.roberthj.musicmaster.service;

import com.roberthj.musicmaster.client.SpotifyApiClientImpl;
import com.roberthj.musicmaster.client.TicketMasterApiClientImpl;
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

    private final SpotifyApiClientImpl spotifyApiClientImpl;

    private final TicketMasterApiClientImpl ticketMasterApiClientImpl;

    public MusicMasterService(
            final SpotifyApiClientImpl spotifyApiClientImpl, final TicketMasterApiClientImpl ticketMasterApiClientImpl) {
        this.spotifyApiClientImpl = spotifyApiClientImpl;
        this.ticketMasterApiClientImpl = ticketMasterApiClientImpl;
    }

    public Artist findEventByArtistName(String artist) throws IOException {

        var artistResponse = spotifyApiClientImpl.getArtistByName(artist);

        // Todo: Pick only if name matches and highest popularity

        var mostPopularArtistOptional = artistResponse
                .stream()
                .filter(item -> item.getName().equalsIgnoreCase(artist))
                .max(Comparator.comparing(Artist::getPopularity));

        //Add events

        if (mostPopularArtistOptional.isEmpty()) {
           throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Artist " + artist + " not found");

        }

        var mostPopularArtist = mostPopularArtistOptional.get();
        mostPopularArtist.setEvents(getEventsForArtist(mostPopularArtist.getName()));

        var relatedArtists = spotifyApiClientImpl.getRelatedArtists(mostPopularArtist.getId());

        //Add events
        relatedArtists
                .stream()
                .forEach(relatedArtist -> {
                    try {
                        relatedArtist.setEvents(getEventsForArtist(relatedArtist.getName()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        //TODO: Can this be done in the stem above already?
        var relaterArtistsWithEvents = relatedArtists.stream()
                .filter(a -> a.getEvents() != null)
                .collect(Collectors.toList());

        //Add related artists
        //TODO: only add if they have events
        mostPopularArtist.setRelatedArtists(relaterArtistsWithEvents);

        //var eventResponse = getEventsForArtist(mostPopularArtist.getName()); //TODO: add country parameter as well
        //TODO: Find events for related artists as well

        return mostPopularArtist;

    }

    private List<Event> getEventsForArtist(String artistName) throws IOException {
        return ticketMasterApiClientImpl.findEventsForArtist(artistName);
    }


    public String lookupEvent(String artist) throws IOException {


        var eventResponse = getEventsForArtist(artist);


        return eventResponse.toString();
    }

}
