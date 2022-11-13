package edu.uiuc.cs427app;


/**
 * This is a data class that store all needed weather information
 * */

public class WeatherInfo {
    private String city;
    private String date;
    private String temperature;
    private String weather;
    private String Humidity;
    private String wind;

    /**
     * @return city
     */
    public String getCity() { return city; }

    /**
     * @param city used to set city variable
     */
    public void setCity(String city) { this.city = city; }

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date used to set date variable
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature used to set temperature variable
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * @param weather used to set weather variable
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * @return humidity
     */
    public String getHumidity() {
        return Humidity;
    }

    /**
     * @param humidity used to set humidity variable
     */
    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    /**
     * @return wind
     */
    public String getWind() {
        return wind;
    }

    /**
     * @param wind used to set wind variable
     */
    public void setWind(String wind) {
        this.wind = wind;
    }
}
