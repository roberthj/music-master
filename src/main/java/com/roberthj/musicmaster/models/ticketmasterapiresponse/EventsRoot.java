package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsRoot {
    private Embedded _embedded;
    private Links _links;
    private Page page;

    public Embedded getEmbedded() {
        return _embedded;
    }

    public Links getLinks() {
        return _links;
    }

    public Page getPage() {
        return page;
    }

}
