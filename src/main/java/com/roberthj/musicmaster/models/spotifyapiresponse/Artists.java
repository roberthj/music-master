package com.roberthj.musicmaster.models.spotifyapiresponse;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artists {
    private String href;
    private ArrayList<Item> items;
    private int limit;
    private Object next;
    private int offset;
    private Object previous;
    private int total;

}
