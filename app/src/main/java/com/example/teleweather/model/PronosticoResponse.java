package com.example.teleweather.model;

import java.util.List;

public class PronosticoResponse {
    public Forecast forecast;

    public static class Forecast {
        public List<ForecastDay> forecastday;
    }

    public static class ForecastDay {
        public String date;
        public Day day;
    }

    public static class Day {
        public float maxtemp_c;
        public float mintemp_c;
        public Condition condition;
    }

    public static class Condition {
        public String text;
    }
}
