package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsRoot {
    @JsonProperty("_embedded")
    private Embedded embedded;
    @JsonProperty("_links")
    private Links links;
    private Page page;

}
