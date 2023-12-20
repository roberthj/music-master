package com.roberthj.musicmaster.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roberthj.musicmaster.models.spotifyapiresponse.Image;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Artist {

  @JsonProperty("external_urls")
  private String externalUrl;
  private ArrayList<String> genres;
  private String href;
  private String id;
  private ArrayList<Image> images;
  private String name;
  private int popularity;
  private String type;
  private String uri;
  private List<Event> events;
  private List<Artist> relatedArtists;

}
