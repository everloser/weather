package com.google.everloser12.fishingweather.prework;

/**
 * Created by al-ev on 29.04.2016.
 */
public class WeatherRequest {
    private double lat;
    private double lon;

    public WeatherRequest(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
