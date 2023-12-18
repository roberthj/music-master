package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roberthj.musicmaster.models.Artist;

import java.util.List;

public interface SpotifyApiClient {

    List<Artist> getArtistByName(String artist) throws JsonProcessingException;

    List<Artist> getRelatedArtists(String id) throws JsonProcessingException;

}
