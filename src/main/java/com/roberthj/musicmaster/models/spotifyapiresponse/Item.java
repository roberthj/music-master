package com.roberthj.musicmaster.models.spotifyapiresponse;

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

}
