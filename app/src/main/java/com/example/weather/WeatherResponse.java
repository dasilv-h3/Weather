package com.example.weather;

public class WeatherResponse {
    private Main main;
    private Wind wind;
    //private Weather weather;
    private String name;

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;
        private long pressure;
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public long getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class Wind {
        private double speed;
        private int deg;

        public double getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }
    }
    /*public static class Weather {
        private String description;
        private String icon;
        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }*/
}
