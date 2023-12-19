package com.roberthj.musicmaster.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class HttpWebClient {

  @Autowired WebClient webClient;

  public String getSyncronously(URI uri, HttpHeaders headers) {

    // TODO: Put in abstract class or something else instead?

      return webClient
              .get()
              .uri(uri)
              .headers(h -> h.addAll(headers))
              .retrieve()
              .onStatus(httpStatus -> !httpStatus.is2xxSuccessful(),
                      clientResponse -> handleErrorResponse(clientResponse.statusCode()))
              .bodyToMono(String.class)
              .block();


    // TODO: What about error handling?

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

    private Mono<? extends Throwable> handleErrorResponse(HttpStatusCode statusCode) {

        // Handle non-success status codes here (e.g., logging or custom error handling)
        return Mono.error(new Exception("Failed to fetch. Status code: " + statusCode));
    }
}
