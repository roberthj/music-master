package com.roberthj.musicmaster.models.spotifyapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RelatedArtistsRoot {
    private ArrayList<Item> artists;

}
