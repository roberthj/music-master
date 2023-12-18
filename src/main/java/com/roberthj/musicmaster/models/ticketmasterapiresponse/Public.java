package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Public {
    private Date startDateTime;
    private boolean startTBD;
    private boolean startTBA;
    private Date endDateTime;
}
