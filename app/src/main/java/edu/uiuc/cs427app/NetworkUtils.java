package edu.uiuc.cs427app;

import android.net.Uri;
import android.util.Log;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private final static String TAG = "NetworkUtils";
    private final static String WEATHER_BASE_URL = "http://api.weatherbit.io/v2.0/current"; // base url for api, alread up to date, do no change
    private final static String API_KEY = "04fd148ab41d4aba85038645b1ffdfbc";   // apikey
    private final static String PARAM_API_KEY = "key"; //parameter for apikey
    private final static String PARAM_LAT_KEY = "lat";  //parameter for latitude
    private final static String PARAM_LON_KEY = "lon";  //parameter for longitude
    private final static String PARAM_UNITS_KEY = "units"; //parameter for unit
    private final static String PARAM_UNITS_VALUE = "I"; // make value to be the Fahrenheit

    /**
     * Convert latitude and longitude to query url for api
     * @param lat: the latitude of the target city
     * @param lon: the longitude of the target city
     * @return url: the URL object
     * */
    public static URL buildUrlForWeather(String lat, String lon){
        Uri builtUri = Uri.parse(WEATHER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LAT_KEY, lat)
                .appendQueryParameter(PARAM_LON_KEY, lon)
                .appendQueryParameter(PARAM_UNITS_KEY, PARAM_UNITS_VALUE).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.i(TAG, "buildUrlForWeather: url: " + url);

        return url;
    }
    /**
     * Make a connection to the api by url
     * @param url: the query url
     * */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            Boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
