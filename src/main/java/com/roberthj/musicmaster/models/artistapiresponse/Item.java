package com.roberthj.musicmaster.models.artistapiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Item {

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


    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    public Followers getFollowers() {
        return followers;
    }
    public ArrayList<String> getGenres() {
        return genres;
    }
    public String getHref() {
        return href;
    }
    public String getId() {
        return id;
    }
    public ArrayList<Image> getImages() {
        return images;
    }
    public String getName() {
        return name;
    }
    public int getPopularity() {
        return popularity;
    }
    public String getType() {
        return type;
    }
    public String getUri() {
        return uri;
    }



}
