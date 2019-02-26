package com.kar.horoscope.world;

public class MonthText {
    public String month;
    public String text;

    public  MonthText( String month, String text ) {
        this.month = month;
        this.text = text;
    }

    public MonthText() { this ( null, null ); }
}
