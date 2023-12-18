package com.roberthj.musicmaster.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roberthj.musicmaster.models.artistapiresponse.ExternalUrls;
import com.roberthj.musicmaster.models.artistapiresponse.Followers;
import com.roberthj.musicmaster.models.artistapiresponse.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Artist {

  @JsonProperty("external_urls")
  public ExternalUrls externalUrls;
  public Followers followers;
  public ArrayList<String> genres;
  public String href;
  public String id;
  public ArrayList<Image> images;
  public String name;
  public int popularity;
  public String type;
  public String uri;

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
}
