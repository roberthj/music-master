package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
