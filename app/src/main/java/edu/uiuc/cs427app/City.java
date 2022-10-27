package edu.uiuc.cs427app;

import java.io.Serializable;

public class City implements Serializable {
    public String cityName;
    public double latitude;
    public double longitude;

    City(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}