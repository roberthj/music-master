package com.roberthj.musicmaster.client;

import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.spotifyapiresponse.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpotifyApiClientImplTest {

    @Mock
    private HttpWebClient httpWebClient;

    @Captor
    ArgumentCaptor<URI> uriCaptor;

    @Captor
    ArgumentCaptor<HttpHeaders> httpHeadersCaptor;

    private SpotifyApiClientImpl spotifyApiClient;

    @BeforeEach
    void setup() {
        spotifyApiClient = new SpotifyApiClientImpl(httpWebClient, "clientId", "clientSecret");
    }

    @Test
    public void verifyThatWebClientIsCalledWithCorrectUriAndHeadersForGetArtistByName() throws IOException {

        var mockedArtistsResponse = getMockedArtistsResponseJson();
        var tokenUrl = UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token").build(true).toUri();

        String credentials = "clientId" + ":" + "clientSecret";

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Authorization", "Basic " + encodedCredentials);
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials";

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedArtistsResponse);
        when(httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody)).thenReturn(getMockedTokenResponseJson());
        spotifyApiClient.getArtistByName("artistName");

        verify(httpWebClient).getSyncronously(uriCaptor.capture(), httpHeadersCaptor.capture());
        var uriCaptorValue = uriCaptor.getValue().toString();

        var headerCaptureValue = httpHeadersCaptor.getValue();

        assertEquals("https://api.spotify.com/v1/search?type=artist&q=artistName", uriCaptorValue);
        assertEquals(getMockedHttpHeaders(), headerCaptureValue);

    }

    @Test
    public void verifyThatWebClientIsCalledWithCorrectUriAndHeadersForGetRelatedArtists() throws IOException {

        var mockedArtistsResponse = getMockedRelatedArtistsResponseJson();
        var tokenUrl = UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token").build(true).toUri();

        String credentials = "clientId" + ":" + "clientSecret";

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Authorization", "Basic " + encodedCredentials);
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials";

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedArtistsResponse);
        when(httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody)).thenReturn(getMockedTokenResponseJson());
        spotifyApiClient.getRelatedArtists("artistId");

        verify(httpWebClient).getSyncronously(uriCaptor.capture(), httpHeadersCaptor.capture());
        var uriCaptorValue = uriCaptor.getValue().toString();

        var headerCaptureValue = httpHeadersCaptor.getValue();

        assertEquals("https://api.spotify.com/v1/artists/artistId/related-artists", uriCaptorValue);
        assertEquals(getMockedHttpHeaders(), headerCaptureValue);

    }


    @Test
    public void verifyThatArtistResponseIsMappedCorrectlyToListOfArtists() throws IOException {

        var mockedArtistsResponse = getMockedArtistsResponseJson();
        var tokenUrl = UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token").build(true).toUri();

        String credentials = "clientId" + ":" + "clientSecret";

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Authorization", "Basic " + encodedCredentials);
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials";

        var expectedArtistsList = getMockedListOfArtists();

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedArtistsResponse);
        when(httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody)).thenReturn(getMockedTokenResponseJson());
        var result = spotifyApiClient.getArtistByName("Metallica");

        assertEquals(expectedArtistsList.get(0), result.get(0));

    }

    @Test
    public void verifyThatRelatedArtistResponseIsMappedCorrectlyToListOfArtists() throws IOException {

        var mockedRelatedArtistsResponse = getMockedRelatedArtistsResponseJson();
        var tokenUrl = UriComponentsBuilder.fromUriString("https://accounts.spotify.com/api/token").build(true).toUri();

        String credentials = "clientId" + ":" + "clientSecret";

        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.set("Authorization", "Basic " + encodedCredentials);
        authHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials";

        var expectedRelatedArtistsList = getMockedListOfRelatedArtists();

        when(httpWebClient.getSyncronously(any(), any())).thenReturn(mockedRelatedArtistsResponse);
        when(httpWebClient.postSyncronously(tokenUrl, authHeaders, requestBody)).thenReturn(getMockedTokenResponseJson());
        var result = spotifyApiClient.getRelatedArtists("artistId");

        assertEquals(expectedRelatedArtistsList.get(0), result.get(0));

    }


    private HttpHeaders getMockedHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer tokenValue");
        return headers;
    }

    private List<Artist> getMockedListOfArtists() {

        var genres = new ArrayList<String>();
        genres.addAll(List.of("hard rock", "metal", "old school thrash", "rock", "thrash metal"));

        var images = new ArrayList<Image>();
        images.addAll(getImages());

        var artist = Artist.builder().externalUrl("https://open.spotify.com/artist/2ye2Wgw4gimLv2eAKyk1NB").genres(genres).href("https://api.spotify.com/v1/artists/2ye2Wgw4gimLv2eAKyk1NB").id("2ye2Wgw4gimLv2eAKyk1NB").images(images).name("Metallica").popularity(80).type("artist").uri("spotify:artist:2ye2Wgw4gimLv2eAKyk1NB").build();

        return List.of(artist);
    }

    private List<Artist> getMockedListOfRelatedArtists() {

        var genres = new ArrayList<String>();
        genres.addAll(List.of("alternative metal", "hard rock", "melodic thrash", "metal", "old school thrash", "rock", "speed metal", "thrash metal"));

        var images = new ArrayList<Image>();
        images.addAll(getImagesRelated());

        var artist1 =
                Artist.builder().externalUrl("https://open.spotify.com/artist/1Yox196W7bzVNZI7RBaPnf")
                        .genres(genres)
                        .href("https://api.spotify.com/v1/artists/1Yox196W7bzVNZI7RBaPnf")
                        .id("1Yox196W7bzVNZI7RBaPnf")
                        .images(images).name("Megadeth")
                        .popularity(68)
                        .type("artist")
                        .uri("spotify:artist:1Yox196W7bzVNZI7RBaPnf")
                        .build();

        return List.of(artist1);
    }

    private List<Image> getImagesRelated() {

        var image1 = new Image();
        image1.setHeight(640);
        image1.setUrl("https://i.scdn.co/image/ab6761610000e5eb79058c0b634631533ed40b22");
        image1.setWidth(640);

        var image2 = new Image();
        image2.setHeight(320);
        image2.setUrl("https://i.scdn.co/image/ab6761610000517479058c0b634631533ed40b22");
        image2.setWidth(320);

        var image3 = new Image();
        image3.setHeight(160);
        image3.setUrl("https://i.scdn.co/image/ab6761610000f17879058c0b634631533ed40b22");
        image3.setWidth(160);


        return List.of(image1, image2, image3);
    }
    private List<Image> getImages() {

        var image1 = new Image();
        image1.setHeight(640);
        image1.setUrl("https://i.scdn.co/image/ab6761610000e5eb69ca98dd3083f1082d740e44");
        image1.setWidth(640);

        var image2 = new Image();
        image2.setHeight(320);
        image2.setUrl("https://i.scdn.co/image/ab6761610000517469ca98dd3083f1082d740e44");
        image2.setWidth(320);

        var image3 = new Image();
        image3.setHeight(160);
        image3.setUrl("https://i.scdn.co/image/ab6761610000f17869ca98dd3083f1082d740e44");
        image3.setWidth(160);


        return List.of(image1, image2, image3);
    }

    private String getMockedTokenResponseJson() {
        return "{\"access_token\":\"tokenValue\",\"token_type\":\"Bearer\",\"expires_in\":3600}";
    }

    private String getMockedArtistsResponseJson() {

        return "{\n" + "  \"artists\": {\n" + "    \"href\": \"https://api.spotify.com/v1/search?query=metallica&type=artist&offset=0&limit=20\",\n" + "    \"items\": [\n" + "      {\n" + "        \"external_urls\": {\n" + "          \"spotify\": \"https://open.spotify.com/artist/2ye2Wgw4gimLv2eAKyk1NB\"\n" + "        },\n" + "        \"followers\": {\n" + "          \"href\": null,\n" + "          \"total\": 26486103\n" + "        },\n" + "        \"genres\": [\n" + "          \"hard rock\",\n" + "          \"metal\",\n" + "          \"old school thrash\",\n" + "          \"rock\",\n" + "          \"thrash metal\"\n" + "        ],\n" + "        \"href\": \"https://api.spotify.com/v1/artists/2ye2Wgw4gimLv2eAKyk1NB\",\n" + "        \"id\": \"2ye2Wgw4gimLv2eAKyk1NB\",\n" + "        \"images\": [\n" + "          {\n" + "            \"height\": 640,\n" + "            \"url\": \"https://i.scdn.co/image/ab6761610000e5eb69ca98dd3083f1082d740e44\",\n" + "            \"width\": 640\n" + "          },\n" + "          {\n" + "            \"height\": 320,\n" + "            \"url\": \"https://i.scdn.co/image/ab6761610000517469ca98dd3083f1082d740e44\",\n" + "            \"width\": 320\n" + "          },\n" + "          {\n" + "            \"height\": 160,\n" + "            \"url\": \"https://i.scdn.co/image/ab6761610000f17869ca98dd3083f1082d740e44\",\n" + "            \"width\": 160\n" + "          }\n" + "        ],\n" + "        \"name\": \"Metallica\",\n" + "        \"popularity\": 80,\n" + "        \"type\": \"artist\",\n" + "        \"uri\": \"spotify:artist:2ye2Wgw4gimLv2eAKyk1NB\"\n" + "      }\n" + "    ],\n" + "    \"limit\": 20,\n" + "    \"next\": null,\n" + "    \"offset\": 0,\n" + "    \"previous\": null,\n" + "    \"total\": 14\n" + "  }\n" + "}";
    }

    private String getMockedRelatedArtistsResponseJson() {
        return "{\n" +
                "  \"artists\" : [ {\n" +
                "    \"external_urls\" : {\n" +
                "      \"spotify\" : \"https://open.spotify.com/artist/1Yox196W7bzVNZI7RBaPnf\"\n" +
                "    },\n" +
                "    \"followers\" : {\n" +
                "      \"href\" : null,\n" +
                "      \"total\" : 5084029\n" +
                "    },\n" +
                "    \"genres\" : [ \"alternative metal\", \"hard rock\", \"melodic thrash\", \"metal\", \"old school thrash\", \"rock\", \"speed metal\", \"thrash metal\" ],\n" +
                "    \"href\" : \"https://api.spotify.com/v1/artists/1Yox196W7bzVNZI7RBaPnf\",\n" +
                "    \"id\" : \"1Yox196W7bzVNZI7RBaPnf\",\n" +
                "    \"images\" : [ {\n" +
                "      \"height\" : 640,\n" +
                "      \"url\" : \"https://i.scdn.co/image/ab6761610000e5eb79058c0b634631533ed40b22\",\n" +
                "      \"width\" : 640\n" +
                "    }, {\n" +
                "      \"height\" : 320,\n" +
                "      \"url\" : \"https://i.scdn.co/image/ab6761610000517479058c0b634631533ed40b22\",\n" +
                "      \"width\" : 320\n" +
                "    }, {\n" +
                "      \"height\" : 160,\n" +
                "      \"url\" : \"https://i.scdn.co/image/ab6761610000f17879058c0b634631533ed40b22\",\n" +
                "      \"width\" : 160\n" +
                "    } ],\n" +
                "    \"name\" : \"Megadeth\",\n" +
                "    \"popularity\" : 68,\n" +
                "    \"type\" : \"artist\",\n" +
                "    \"uri\" : \"spotify:artist:1Yox196W7bzVNZI7RBaPnf\"\n" +
                "  }]\n" +
                "}";
    }
}
