package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Venue {
    private String href;
    private String name;
    private String type;
    private String id;
    private boolean test;
    private String url;
    private String locale;
    private String postalCode;
    private String timezone;
    private City city;
    private State state;
    private Country country;
    private Address address;
    private Location location;
    private UpcomingEvents upcomingEvents;
    private Links _links;
}
