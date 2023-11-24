package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static String API_KEY = "c7dce16be3241dc3cb1c615ba1123694";
    public String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidLocation.checkPermission(this);

        //Interaction avec la localisation
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                OpenWeatherMapService service = ApiClient.getInstance().create(OpenWeatherMapService.class);
                Call<WeatherResponse> call = service.getCurrentWeatherByLocation(latitude,longitude, API_KEY, "metric", "fr");
                call.enqueue(new Callback<WeatherResponse>() {

                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse weatherResponse = response.body();
                            if (weatherResponse != null) {
                                Log.d("API_CALL", "WeatherResponse is OK");
                                updateUI(weatherResponse);
                            } else {
                                Log.e("API_CALL", "WeatherResponse is null");
                            }
                        } else {
                            int errorCode = response.code();
                            Log.e("API_CALL", "Error: " + errorCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("API_CALL_FAILURE", "Error: " + t.getMessage(), t);
                    }
                    private void updateUI(WeatherResponse weatherResponse) {
                        TextView textViewName = findViewById(R.id.city);
                        TextView textViewTemp = findViewById(R.id.city_temp);
                        TextView textViewSpeedWind = findViewById(R.id.speed_wind);
                        TextView textViewWindDirection = findViewById(R.id.wind_direction);
                        TextView textViewMinTemp = findViewById(R.id.min_temp_day);
                        TextView textViewMaxTemp = findViewById(R.id.max_temp_day);
                        TextView textViewPressure = findViewById(R.id.pressure);
                        TextView textViewHumidity = findViewById(R.id.humidity);

                        String city = weatherResponse.getName();
                        double temperature = weatherResponse.getMain().getTemp();
                        double speed = weatherResponse.getWind().getSpeed();
                        int direction = weatherResponse.getWind().getDeg();
                        double min_temp = weatherResponse.getMain().getTemp_min();
                        double max_temp = weatherResponse.getMain().getTemp_max();
                        long pressure = weatherResponse.getMain().getPressure();
                        int humidity = weatherResponse.getMain().getHumidity();

                        String displayName = city;
                        String displayTemp = Math.round(temperature) + " °C";
                        String displaySpeedWind = "Vent:\n " + speed + "m/sec";
                        String displayWindDirection = "Direction:\n " + direction + " °";
                        String displayMinTemp = "Temp min:\n " + Math.round(min_temp) + " °C";
                        String displayMaxTemp = "Temp max:\n " + Math.round(max_temp) + " °C";
                        String displayPressure = "Pression:\n " + pressure + " hPa";
                        String displayHumidity = "Humidité:\n " + humidity + " %";

                        textViewName.setText(displayName);
                        textViewTemp.setText(displayTemp);
                        textViewSpeedWind.setText(displaySpeedWind);
                        textViewWindDirection.setText(displayWindDirection);
                        textViewMinTemp.setText(displayMinTemp);
                        textViewMaxTemp.setText(displayMaxTemp);
                        textViewPressure.setText(displayPressure);
                        textViewHumidity.setText(displayHumidity);
                    }
                });

            }

        };
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);


        SearchView search_bar_city = findViewById(R.id.search_bar);
        search_bar_city.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("processSearchQuery", "City" + query);
                city = query;
                makeWeatherApiCallCity(city);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                city = newText;
                return false;
            }
        });
        // Mise à jour de localisation
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }
    public void makeWeatherApiCallCity(String city) {

        OpenWeatherMapService service = ApiClient.getInstance().create(OpenWeatherMapService.class);
        Call<WeatherResponse> call = service.getCurrentWeather(city, API_KEY, "metric", "fr");
        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        Log.d("API_CALL", "WeatherResponse is OK");
                        updateUI(weatherResponse);
                    } else {
                        Log.e("API_CALL", "WeatherResponse is null");
                    }
                } else {
                    int errorCode = response.code();
                    Log.e("API_CALL", "Error: " + errorCode);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("API_CALL_FAILURE", "Error: " + t.getMessage(), t);
            }
            private void updateUI(WeatherResponse weatherResponse) {
                TextView textViewName = findViewById(R.id.city);
                TextView textViewTemp = findViewById(R.id.city_temp);
                TextView textViewSpeedWind = findViewById(R.id.speed_wind);
                TextView textViewWindDirection = findViewById(R.id.wind_direction);
                TextView textViewMinTemp = findViewById(R.id.min_temp_day);
                TextView textViewMaxTemp = findViewById(R.id.max_temp_day);
                TextView textViewPressure = findViewById(R.id.pressure);
                TextView textViewHumidity = findViewById(R.id.humidity);

                String city = weatherResponse.getName();
                double temperature = weatherResponse.getMain().getTemp();
                double speed = weatherResponse.getWind().getSpeed();
                int direction = weatherResponse.getWind().getDeg();
                double min_temp = weatherResponse.getMain().getTemp_min();
                double max_temp = weatherResponse.getMain().getTemp_max();
                long pressure = weatherResponse.getMain().getPressure();
                int humidity = weatherResponse.getMain().getHumidity();

                String displayName = city;
                String displayTemp = Math.round(temperature) + " °C";
                String displaySpeedWind = "Vitesse du vent: " + speed + "m/sec";
                String displayWindDirection = "Direction du vent: " + direction + " °";
                String displayMinTemp = "Température minimum: " + Math.round(min_temp) + " °C";
                String displayMaxTemp = "Température maximum: " + Math.round(max_temp) + " °C";
                String displayPressure = "Pression atmosphérique: " + pressure + " hPa";
                String displayHumidity = "Humidité de l'air: " + humidity + " %";

                textViewName.setText(displayName);
                textViewTemp.setText(displayTemp);
                textViewSpeedWind.setText(displaySpeedWind);
                textViewWindDirection.setText(displayWindDirection);
                textViewMinTemp.setText(displayMinTemp);
                textViewMaxTemp.setText(displayMaxTemp);
                textViewPressure.setText(displayPressure);
                textViewHumidity.setText(displayHumidity);
            }
        });
    }
}