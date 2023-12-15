package com.roberthj.musicmaster.resource;

import com.roberthj.musicmaster.client.SpotifyApiAuth;
import com.roberthj.musicmaster.service.MusicMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class MusicMasterController {

  private final MusicMasterService musicMasterService;

  public MusicMasterController(final MusicMasterService musicMasterService) {
    this.musicMasterService = musicMasterService;
  }

  @GetMapping("/hello")
  public String hello() {

    return "Hello!";
  }

  @GetMapping("/lookup_artist")
  public String lookUpArtist() {

    var artist_id = musicMasterService.lookupArtistId();
    return artist_id;
  }
}
