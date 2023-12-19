package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class EventTmResponse {
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

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }
    public boolean getTest() {
        return test;
    }
    public String getUrl() {
        return url;
    }
    public String getLocale() {
        return locale;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
    public Sales getSales() {
        return sales;
    }

    public Dates getDates() {
        return dates;
    }
    public String getPleaseNote() {
        return pleaseNote;
    }

    public Embedded getEmbedded() {
        return _embedded;
    }



}
