package edu.uiuc.cs427app;

import java.io.Serializable;

/**
 * The city class represents a city in the list that users can save
 *
 * Implements serializable for file saving
 */
public class City implements Serializable {
    public String cityName;
    public double latitude;
    public double longitude;

    /**
     * City is the default constructor
     *
     * @param cityName will be displayed to the user on the main activity and details page
     * @param latitude is used with the maps and weather API to calculate the location
     * @param longitude is used with the maps and weather API to calculate the location
     */
    City(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
