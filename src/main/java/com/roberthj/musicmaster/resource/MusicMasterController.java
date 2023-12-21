package com.roberthj.musicmaster.resource;

import com.roberthj.musicmaster.models.Artist;
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
//TODO: Fix all exception handling
  @GetMapping("/find_events/{artist}")
  public Artist findEventByArtistName(@PathVariable(value = "artist") String artist) {

    return musicMasterService.findEventByArtistName(artist);

  }

  //TODO: Add endpoint for saving my events in db

  //TODO: Add endpoint for saving my fetching my events from db
}
