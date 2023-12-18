package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Access {
    private Date startDateTime;
    private boolean startApproximate;
    private boolean endApproximate;
}
