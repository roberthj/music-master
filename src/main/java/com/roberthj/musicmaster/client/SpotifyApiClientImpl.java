package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.spotifyapiresponse.ArtistsRoot;
import com.roberthj.musicmaster.models.spotifyapiresponse.Item;
import com.roberthj.musicmaster.models.spotifyapiresponse.RelatedArtistsRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Service
public class SpotifyApiClientImpl implements SpotifyApiClient {

    public static final String BASE_URI_SPOTIFY = "https://api.spotify.com/v1";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String clientId;

    private String clientSecret;

    private final HttpWebClient httpWebClient;

    public SpotifyApiClientImpl(HttpWebClient httpWebClient,
                                @Value("${spotify.api.client_id}") String clientId,
                                @Value("${spotify.api.client_secret}") String clientSecret) {
        this.httpWebClient = httpWebClient;
        this.clientId = clientId;
        this.clientSecret=clientSecret;
    }

    public List<Artist> getArtistByName(String artist) throws JsonProcessingException {

        var accessToken = getAccessToken();

        var uri = generateFullSearchUri("/search", "artist", artist);

        HttpHeaders headers = getHttpHeaders(accessToken);

        var response = httpWebClient.getSyncronously(uri, headers);

        var responseObject = objectMapper.readValue(response, ArtistsRoot.class);

        return extractArtist(responseObject.getArtists().getItems());

    }


    public List<Artist> getRelatedArtists(String id) throws JsonProcessingException {
        var accessToken = getAccessToken(); //TODO: can I resuse the token from the call before?

        var uri = generateRelatedArtistsUri(id, "/related-artists");

        HttpHeaders headers = getHttpHeaders(accessToken);

        var response = httpWebClient.getSyncronously(uri, headers);

        var responseObject = objectMapper.readValue(response, RelatedArtistsRoot.class);


        var uu = extractArtist(responseObject.getArtists());

        return uu;
    }

    private URI generateFullSearchUri(String path, String type, String value) {

        return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY + path)
                .queryParam("type", type)
                .queryParam("q", URLEncoder.encode(value))
                .build(true)
                .toUri();
    }

    private URI generateRelatedArtistsUri(String id, String path) {

        return UriComponentsBuilder.fromUriString(BASE_URI_SPOTIFY + "/artists/" + id + path)
                .build(true)
                .toUri();
    }

    private static HttpHeaders getHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private String getAccessToken() throws JsonProcessingException {

        var tokenUrl =
                UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token")
                        .build(true)
                        .toUri();

        String credentials = clientId + ":" + clientSecret;

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Authorization", "Basic " + encodedCredentials);
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials";

        var authTokenResponse = httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody);

        SpotifyApiAuthResponse
                authResponse = objectMapper.readValue(authTokenResponse, SpotifyApiAuthResponse.class);

        return authResponse.getAccessToken();
    }

    private List<Artist> extractArtist(List<Item> items) {

        return items.stream()
                .map(
                        item -> {
                            return Artist
                                    .builder()
                                    .externalUrl(item.getExternalUrls().getSpotify())
                                    .genres(item.getGenres())
                                    .href(item.getHref())
                                    .id(item.getId())
                                    .images(item.getImages())
                                    .name(item.getName())
                                    .popularity(item.getPopularity())
                                    .type(item.getType())
                                    .uri(item.getUri())
                                    .build();

                        })
                .toList();


    }


}
