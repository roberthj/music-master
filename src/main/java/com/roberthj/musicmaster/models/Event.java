package com.roberthj.musicmaster.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Event {

    private String name;
    private String type;
    private String id;
    private String url;
    private String startDate;
    private String notes;
    private String venue;
    private String address;
    private String city;
    private String country;

}
