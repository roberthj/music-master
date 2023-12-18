package com.roberthj.musicmaster.resource;

import com.roberthj.musicmaster.service.MusicMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
  public String findEventByArtistName(@PathVariable(value = "artist") String artist) throws IOException {

    return  musicMasterService.findEventByArtistName(artist);

  }

  @GetMapping("/lookup_event/{artist}")
  public String lookUpConcert(@PathVariable(value = "artist") String artist) throws IOException {

    return  musicMasterService.lookupEvent(artist);

  }
}
