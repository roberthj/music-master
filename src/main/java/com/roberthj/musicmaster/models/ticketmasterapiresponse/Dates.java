package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dates {
    private Access access;
    private Start start;
    private End end;
    private String timezone;
    private Status status;
    private boolean spanMultipleDays;

}
