package com.roberthj.musicmaster.service;

import com.roberthj.musicmaster.client.SpotifyApiClientImpl;
import com.roberthj.musicmaster.client.TicketMasterApiClientImpl;
import com.roberthj.musicmaster.models.Artist;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

  public String findEventByArtistName(String artist) throws IOException {

    var artistResponse = spotifyApiClientImpl.getArtistByName(artist);

    // Todo: Pick only if name matches and highest popularity

    var mostPopularArtist = artistResponse
            .stream()
            .filter(item -> item.getName().equalsIgnoreCase(artist))
            .max(Comparator.comparing(Artist::getPopularity)).get(); //TODO: Handle optional

    //TODO: Get related artists

    var relatedArtists = spotifyApiClientImpl.getRelatedArtists(mostPopularArtist.getId());

    mostPopularArtist.setRelatedArtists(relatedArtists);

    var eventResponse = ticketMasterApiClientImpl.findEventsForArtist(mostPopularArtist.getName()); //TODO: add country parameter as well
    //TODO: Find events for related artists as well

    return "artistResponse";
  }





  public String lookupEvent(String artist) throws IOException {


    var eventResponse = ticketMasterApiClientImpl.findEventsForArtist(artist);


    return eventResponse;
  }

}
