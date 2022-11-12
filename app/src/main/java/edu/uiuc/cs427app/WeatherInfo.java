package edu.uiuc.cs427app;


/**
 * This is a data class that store all needed weather information
 * */

public class WeatherInfo {
    String city;
    String date;
    String temperature;
    String weather;
    String Humidity;
    String wind;

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
