package com.example.kar.horoscope.world;

public class Text {
    public String date;
    public String text;

    public  Text( String date, String text ) {
        this.date = date;
        this.text = text;
    }

    public Text() { this ( null, null ); }
}
