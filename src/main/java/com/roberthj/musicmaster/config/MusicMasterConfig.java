package com.roberthj.musicmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MusicMasterConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder().build();
  }

}
