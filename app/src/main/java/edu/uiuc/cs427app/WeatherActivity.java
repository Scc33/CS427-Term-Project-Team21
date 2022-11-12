package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "WeatherActivity";
    private TextView currentDate;
    private TextView city;
    private TextView weatherDescription;
    private TextView temperature;
    private TextView humidity;
    private TextView wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("cities");
        ArrayList<City> cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");
        int cityIdx = intent.getIntExtra("cityIdx", 0);
        String latitude = String.valueOf(cityList.get(cityIdx).latitude);
        String longitude = String.valueOf(cityList.get(cityIdx).longitude);

        Log.i(TAG, "onCreate input Latitude: " + latitude + " longitude: " + longitude);

        currentDate = findViewById(R.id.current_date_tv);
        city = findViewById(R.id.city_name_tv);
        weatherDescription = findViewById(R.id.weather_Dscp_tv);
        temperature = findViewById(R.id.temperature_tv);
        humidity = findViewById(R.id.humidity_tv);
        wind = findViewById(R.id.wind_tv);

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> {
            finish();
        });

        URL weatherUrl = NetworkUtils.buildUrlForWeather(latitude, longitude);
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);

    }

    /**
     * Fetch weather details from api
     * @returns JSON data of weather details
     * */
    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherSearchResults = null;

            try{
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            }catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults);
            return weatherSearchResults;
        }

        @Override
        protected void onPostExecute(String weatherSearchResults) {
            if (weatherSearchResults != null && !weatherSearchResults.equals("")){
                WeatherInfo weatherData = parseJSON(weatherSearchResults);
                Log.i(TAG, "Parsed results date " + weatherData.getDate()
                        +  "\nParsed results temp " + weatherData.getTemperature());
                updateWeatherPage(weatherData);
            }
            super.onPostExecute(weatherSearchResults);
        }
    }

    /**
     * Parse the JSON data to a WeatherInfo object
     * @param weatherSearchResults: JSON string of weather details
     * @return a WeatherInfo object
     * */
    private WeatherInfo parseJSON(String weatherSearchResults){
        WeatherInfo weather_info = new WeatherInfo();

        try{
            JSONObject rootObject = new JSONObject((weatherSearchResults));
            JSONArray data = rootObject.getJSONArray("data");
            JSONObject resultsObj = data.getJSONObject(0);

            String city = resultsObj.getString("city_name");
            String date = resultsObj.getString("ob_time");
            String temperature = resultsObj.getString("temp");
            String humidity = resultsObj.getString("rh");
            String wind = resultsObj.getString("wind_spd");
            JSONObject weather_detail = resultsObj.getJSONObject("weather");

            date = date.substring(0, date.indexOf(' '));
            String weather = weather_detail.getString("description");

            weather_info.setCity(city);
            weather_info.setDate(date);
            weather_info.setTemperature(temperature + " F");
            weather_info.setHumidity(humidity + " %");
            weather_info.setWind(wind + " mph");
            weather_info.setWeather(weather);

            Log.i(TAG, "parse results: " + city + "  " + date);



        }catch (JSONException e){
            e.printStackTrace();
        }
        return weather_info;
    }

    /**
     * Update information in UI
     * @param weatherData: a weatherInfo object
     * */
    private void updateWeatherPage(WeatherInfo weatherData){
        currentDate.setText(weatherData.getDate());
        city.setText(weatherData.getCity());
        weatherDescription.setText(weatherData.getWeather());
        temperature.setText(weatherData.getTemperature());
        humidity.setText(weatherData.getHumidity());
        wind.setText(weatherData.getWind());
    }


}