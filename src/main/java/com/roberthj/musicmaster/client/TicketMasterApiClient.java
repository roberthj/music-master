package com.roberthj.musicmaster.client;

import com.roberthj.musicmaster.models.Event;

import java.util.List;

public interface TicketMasterApiClient {

    List<Event> findEventsForArtist(String artist) ;
}
