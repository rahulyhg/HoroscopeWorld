package com.kar.horoscope.world;

public class WeekText {
    public String week;
    public String text;

    public  WeekText( String week, String text ) {
        this.week = week;
        this.text = text;
    }

    public WeekText() { this ( null, null ); }
}
