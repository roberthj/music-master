package com.roberthj.musicmaster.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TicketMasterApiClient {

  //TODO: Put interface before this class

  @Autowired WebClient webClient;

  public String findEventsForArtist(URI uri) {

    return getSyncronously(uri);

  }

  private String getSyncronously(URI uri)  {
    //TODO: Put in abstract class or something else and reuse with spotify client
    return webClient
        .get()
        .uri(uri)
        .header(null)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    //TODO: What about error handling?
  }



}
