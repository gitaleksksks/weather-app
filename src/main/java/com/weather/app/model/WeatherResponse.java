package com.weather.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    private Main main;
    private Weather[] weather;

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public Weather[] getWeather() { return weather; }
    public void setWeather(Weather[] weather) { this.weather = weather; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private double temp;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private String description;
        private String main;

        public String getMain() { return main; }

        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
