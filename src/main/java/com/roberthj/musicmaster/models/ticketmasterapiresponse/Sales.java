package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sales {
    @JsonProperty("public")
    private Public mypublic;
}
