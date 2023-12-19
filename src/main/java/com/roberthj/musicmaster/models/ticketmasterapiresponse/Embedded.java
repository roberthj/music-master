package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Embedded {
    private ArrayList<EventTmResponse> events;
    private ArrayList<Venue> venues;

    public ArrayList<EventTmResponse> getEvents() {
        return events;
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }
}
