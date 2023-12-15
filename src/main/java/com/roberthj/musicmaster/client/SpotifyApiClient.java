package com.roberthj.musicmaster.client;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpotifyApiClient {

  @Autowired
  private WebClient webClient;

  public String getSyncronously(URI uri, HttpHeaders headers) {


    return webClient
        .get()
        .uri(uri)
        .headers(h -> h.addAll(headers))
        .retrieve()
        .bodyToMono(String.class)
        .block();

    //TODO: What about error handling?
  }

  public String postSyncronously(URI uri, HttpHeaders headers, String body) {

    return webClient
        .post()
        .uri(uri)
        .headers(h -> h.addAll(headers))
        .body(Mono.just(body), String.class)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    //TODO: What about error handling?
    // some examples here https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
  }


}
