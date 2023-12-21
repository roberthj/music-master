package com.roberthj.musicmaster.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SpotifyApiAuthResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("expires_in")
  private int expiresIn;

//  @JsonIgnore
//  private LocalDateTime expiresTime;

//  public SpotifyApiAuthResponse(String accessToken, String tokenType, int expiresIn) {
//    this.accessToken=accessToken;
//    this.tokenType=tokenType;
//    this.expiresIn = expiresIn;
   // this.expiresTime = LocalDateTime.now().plusSeconds(expiresIn);

 // }

}
