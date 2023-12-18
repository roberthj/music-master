package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface TicketMasterApiClient {

    String findEventsForArtist(String artist) throws IOException;
}
