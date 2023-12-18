package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Start {
    private String localDate;
    private String localTime;
    private Date dateTime;
    private boolean dateTBD;
    private boolean dateTBA;
    private boolean timeTBA;
    private boolean noSpecificTime;
}
