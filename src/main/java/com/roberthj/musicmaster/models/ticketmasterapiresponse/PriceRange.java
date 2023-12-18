package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceRange {
    private String type;
    private String currency;
    private double min;
    private double max;
}
