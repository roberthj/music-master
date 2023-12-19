package com.roberthj.musicmaster.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roberthj.musicmaster.models.spotifyapiresponse.ExternalUrls;
import com.roberthj.musicmaster.models.spotifyapiresponse.Followers;
import com.roberthj.musicmaster.models.spotifyapiresponse.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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


  public ExternalUrls getExternalUrls() {
    return externalUrls;
  }

  public void setExternalUrls(ExternalUrls externalUrls) {
    this.externalUrls = externalUrls;
  }

  public Followers getFollowers() {
    return followers;
  }

  public void setFollowers(Followers followers) {
    this.followers = followers;
  }

  public ArrayList<String> getGenres() {
    return genres;
  }

  public void setGenres(ArrayList<String> genres) {
    this.genres = genres;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ArrayList<Image> getImages() {
    return images;
  }

  public void setImages(ArrayList<Image> images) {
    this.images = images;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPopularity() {
    return popularity;
  }

  public void setPopularity(int popularity) {
    this.popularity = popularity;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public List<Artist> getRelatedArtists() {
    return relatedArtists;
  }

  public void setRelatedArtists(List<Artist> relatedArtists) {
    this.relatedArtists = relatedArtists;
  }
  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }
}
