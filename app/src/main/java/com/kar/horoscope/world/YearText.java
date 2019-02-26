package com.kar.horoscope.world;

public class YearText {
    public String year;
    public String text;

    public  YearText( String year, String text ) {
        this.year = year;
        this.text = text;
    }

    public YearText() { this ( null, null ); }
}
