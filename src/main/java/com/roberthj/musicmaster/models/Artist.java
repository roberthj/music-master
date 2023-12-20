package com.roberthj.musicmaster.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roberthj.musicmaster.models.spotifyapiresponse.ExternalUrls;
import com.roberthj.musicmaster.models.spotifyapiresponse.Followers;
import com.roberthj.musicmaster.models.spotifyapiresponse.Image;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Artist {

  @JsonProperty("external_urls")
  private ExternalUrls externalUrls;
  private Followers followers;
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
