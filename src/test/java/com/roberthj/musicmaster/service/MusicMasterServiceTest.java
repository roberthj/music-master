package com.roberthj.musicmaster.service;

import com.roberthj.musicmaster.client.SpotifyApiClient;
import com.roberthj.musicmaster.client.TicketMasterApiClient;
import com.roberthj.musicmaster.models.Artist;
import com.roberthj.musicmaster.models.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MusicMasterServiceTest {

    @Mock
    private SpotifyApiClient spotifyApiClient;

    @Mock
    private TicketMasterApiClient ticketMasterApiClient;


    @InjectMocks
    private MusicMasterService musicMasterService;

    @Test
    public void findEventByArtistNameReturnsOnlyMostPopularArtistWithExactName() throws IOException {

        when(spotifyApiClient.getArtistByName(anyString())).thenReturn(mockedArtistResponse("artistName"));
        when(spotifyApiClient.getRelatedArtists(anyString())).thenReturn(mockedRelatedArtistResponse());
        when(ticketMasterApiClient.findEventsForArtist(anyString())).thenReturn(mockedEventsResponse());


        var result = musicMasterService.findEventByArtistName("artistName");

        assertEquals(result.getId(),"artistId");

    }

    @Test
    public void findEventByArtistNameThrowsResponseStatusExceptionWhenArtistIsnotFound() throws IOException {

        when(spotifyApiClient.getArtistByName(anyString())).thenReturn(mockedArtistResponse("artistName"));

        assertThrows(ResponseStatusException.class, () -> musicMasterService.findEventByArtistName("artistNameX"));

    }


    @Test
    public void findEventByArtistNameReturnsRetaledArtistsOnlyIfTheyHaveEvents() throws IOException {

        when(spotifyApiClient.getArtistByName(anyString())).thenReturn(mockedArtistResponse("artistName"));
        when(spotifyApiClient.getRelatedArtists("artistId")).thenReturn(mockedRelatedArtistResponse());
        when(ticketMasterApiClient.findEventsForArtist("artistName")).thenReturn(mockedEventsResponse());
        when(ticketMasterApiClient.findEventsForArtist("relArtistName")).thenReturn(mockedEventsResponse());
        when(ticketMasterApiClient.findEventsForArtist("relArtistName2")).thenReturn(null);

        var result = musicMasterService.findEventByArtistName("artistName");

        assertEquals(result.getRelatedArtists().size(),1);

    }



    private List<Event> mockedEventsResponse() {

        var event = Event
                .builder()
                .name("eventName")
                .build();

        return List.of(event);

    }

    private List<Artist> mockedRelatedArtistResponse() {
        var relArtist1 = Artist.builder()
                .id("relArtistId")
                .name("relArtistName")
                .build();

        var relArtist2 = Artist.builder()
                .id("relArtistId2")
                .name("relArtistName2")
                .build();


        return List.of(relArtist1, relArtist2);

    }

    private List<Artist> mockedArtistResponse(String artistName) {


        var artist1 = Artist.builder()
                .id("artistId")
                .name(artistName)
                .popularity(100)
                .build();

        var artist2 = Artist.builder()
                .id("artistId2")
                .name(artistName+2)
                .popularity(100)
                .build();

        var artist3 = Artist.builder()
                .id("artistId3")
                .name(artistName)
                .popularity(80)
                .build();

        return List.of(artist1, artist2, artist3);

    }

}
