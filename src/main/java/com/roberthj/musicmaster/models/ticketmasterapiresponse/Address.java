package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String line1;

    public String getLine1() {
        return line1;
    }
}
