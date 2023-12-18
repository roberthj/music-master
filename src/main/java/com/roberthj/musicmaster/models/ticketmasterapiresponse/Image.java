package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {
    private String ratio;
    private String url;
    private int width;
    private int height;
    private boolean fallback;
}
