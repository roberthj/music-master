package com.roberthj.musicmaster.models.artistapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Artists {
    private String href;
    private ArrayList<Item> items;
    private int limit;
    private Object next;
    private int offset;
    private Object previous;
    private int total;

    public ArrayList<Item> getItems() {
        return items;
    }
}
