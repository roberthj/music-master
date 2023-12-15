package com.roberthj.musicmaster.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roberthj.musicmaster.service.MusicMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/lookup_artist/{artist}")
  public String lookUpArtist(@PathVariable(value = "artist") String artist) throws JsonProcessingException {

    var artist_id = musicMasterService.lookupArtistId(artist);
    return artist_id;
  }
}
