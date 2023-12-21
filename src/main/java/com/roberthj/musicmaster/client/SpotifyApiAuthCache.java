package com.roberthj.musicmaster.client;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SpotifyApiAuthCache {

    private String token;
    private LocalDateTime expiresAt;

}
