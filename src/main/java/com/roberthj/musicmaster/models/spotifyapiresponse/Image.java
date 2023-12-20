package com.roberthj.musicmaster.models.spotifyapiresponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Image {
    private int height;
    private String url;
    private int width;
}
