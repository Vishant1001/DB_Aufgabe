package com.example.demo;

public class Station {

    private String evaNr;
    private String ds100;
    private String name;

    private String verkehr;

    private String laenge;

    private String breite;

    public Station(String evaNr, String ds100, String name,String verkehr,String laenge,String breite) {
        this.evaNr = evaNr;
        this.ds100 = ds100;
        this.name = name;
        this.verkehr=verkehr;
        this.laenge=laenge;
        this.breite=breite;
    }
    public String getEvaNr() {
        return evaNr;
    }
    public String getDs100() {
        return ds100;
    }
    public String getName() {
        return name;
    }
    public String getVerkehr(){
        return verkehr;
    }
    public String getBreite() {
        return breite;
    }
    public String getLaenge() {
        return laenge;
    }

    @Override
    public String toString() {
        return evaNr + " - " + ds100 + " - " + name;
    }
}
