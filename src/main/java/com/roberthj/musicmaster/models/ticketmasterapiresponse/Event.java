package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Event {
    private String name;
    private String type;
    private String id;
    private boolean test;
    private String url;
    private String locale;
    private ArrayList<Image> images;
    private Sales sales;
    private Dates dates;
    private ArrayList<Classification> classifications;
    private String info;
    private String pleaseNote;
    private ArrayList<PriceRange> priceRanges;
    private Links _links;
    private Embedded _embedded;
}
