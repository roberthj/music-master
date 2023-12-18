package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Embedded {
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;
}
