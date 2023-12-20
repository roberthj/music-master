package com.roberthj.musicmaster.resource;

import com.roberthj.musicmaster.service.MusicMasterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MusicMasterControllerTest {

    public static final String ARTIST = "artist name";

        @Mock
        private MusicMasterService musicMasterService;

        @InjectMocks
        private MusicMasterController musicMasterController;

    @Test
    public void findEventByArtistNameCallsMusicMasterService() throws IOException {
        musicMasterController.findEventByArtistName(ARTIST);
        verify(musicMasterService, times(1)).findEventByArtistName(ARTIST);
    }

}
