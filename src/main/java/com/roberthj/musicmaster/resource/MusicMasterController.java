package com.roberthj.musicmaster.resource;

import com.roberthj.musicmaster.client.SpotifyApiAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class MusicMasterController {

  private final SpotifyApiAuth spotifyApiAuth;

  public MusicMasterController(final SpotifyApiAuth spotifyApiAuth) {
    this.spotifyApiAuth = spotifyApiAuth;
  }

  @GetMapping("/hello")
  public String hello() {

    return "Hello!";
  }

  @GetMapping("/auth")
  public String auth() {

    var accessToken = spotifyApiAuth.getAccessToken();
    return accessToken;
  }
}
