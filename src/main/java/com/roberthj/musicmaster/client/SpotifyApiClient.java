package com.roberthj.musicmaster.client;

import com.roberthj.musicmaster.models.Artist;

import java.util.List;

public interface SpotifyApiClient {

    List<Artist> getArtistByName(String artist) ;

    List<Artist> getRelatedArtists(String id);

}
