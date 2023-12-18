package com.roberthj.musicmaster.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roberthj.musicmaster.client.SpotifyApiClientImpl;
import com.roberthj.musicmaster.client.TicketMasterApiClientImpl;
import com.roberthj.musicmaster.models.Artist;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class MusicMasterService {

  private final SpotifyApiClientImpl spotifyApiClientImpl;

  private final TicketMasterApiClientImpl ticketMasterApiClientImpl;

  public MusicMasterService(
          final SpotifyApiClientImpl spotifyApiClientImpl, final TicketMasterApiClientImpl ticketMasterApiClientImpl) {
    this.spotifyApiClientImpl = spotifyApiClientImpl;
    this.ticketMasterApiClientImpl = ticketMasterApiClientImpl;
  }

  public String lookupArtistId(String artist) throws JsonProcessingException {

    var artistResponse = spotifyApiClientImpl.getArtistByName(artist);

    // Todo: Pick only if name matches and highest popularity

    var mostPopularArtist = artistResponse
            .stream()
            .filter(item -> item.getName().equalsIgnoreCase(artist))
            .max(Comparator.comparing(Artist::getPopularity)).get(); //TODO: Handle optional


    return "artistResponse";
  }

  public String lookupEvent(String artist) {


    var eventResponse = ticketMasterApiClientImpl.findEventsForArtist(artist);

    // Todo: Parse response and pick the most popular artist if more than one exists

    return eventResponse;
  }

}
