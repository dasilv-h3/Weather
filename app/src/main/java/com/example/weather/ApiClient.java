package com.example.weather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    //private static final String ICON_URL = "http://openweathermap.org/img/w/";
    private static Retrofit retrofit;
    public static Retrofit getInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*public static Retrofit getIcon() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ICON_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
}
