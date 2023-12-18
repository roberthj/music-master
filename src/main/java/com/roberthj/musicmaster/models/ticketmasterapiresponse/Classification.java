package com.roberthj.musicmaster.models.ticketmasterapiresponse;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.Segment;
@Getter
@Setter
public class Classification {
    private boolean primary;
    private Segment segment;
    private Genre genre;
    private SubGenre subGenre;
    private Type type;
    private SubType subType;
    private boolean family;
}
