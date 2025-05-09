package com.example.teleweather.model;

public class Pronostico {
    public String date;
    public Dia day;

    public static class Dia {
        public float maxtemp_c;
        public float mintemp_c;
        public Condicion condition;
    }

    public static class Condicion {
        public String text;
    }
}


