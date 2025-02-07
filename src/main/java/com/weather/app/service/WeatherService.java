package com.weather.app.service;

import com.weather.app.model.WeatherHistory;
import com.weather.app.model.WeatherResponse;
import com.weather.app.repository.WeatherHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.base.url}")
    private String baseUrl;

    @Value("${openweathermap.units}")
    private String units;

    @Value("${openweathermap.lang}")
    private String lang;

    private final RestTemplate restTemplate;
    private final WeatherHistoryRepository historyRepository;

    public WeatherService(RestTemplate restTemplate, WeatherHistoryRepository historyRepository) {
        this.restTemplate = restTemplate;
        this.historyRepository = historyRepository;
    }

    public List<WeatherHistory> getAllHistory() {
        return historyRepository.findAll();
    }

    public String getWeatherDescription(double lat, double lon) {
        String url = String.format("%s?lat=%s&lon=%s&units=%s&lang=%s&appid=%s",
                baseUrl, lat, lon, units, lang, apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
        return response.getBody().getWeather()[0].getDescription();
    }

    public String getWeatherMain(double lat, double lon) {
        String url = String.format("%s?lat=%s&lon=%s&units=%s&lang=%s&appid=%s",
                baseUrl, lat, lon, units, lang, apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
        return response.getBody().getWeather()[0].getMain();
    }

    public String getWeatherTemperature(double lat, double lon) {
        String url = String.format("%s?lat=%s&lon=%s&units=%s&lang=%s&appid=%s",
                baseUrl, lat, lon, units, lang, apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
        return response.getBody().getMain().getTemp() + "°C";
    }

    public void saveWeatherHistory(double lat, double lon, String temperature, String description) {
        WeatherHistory history = new WeatherHistory();
        history.setRequestDate(LocalDate.now());
        history.setRequestTime(LocalTime.now());
        history.setLatitude(lat);
        history.setLongitude(lon);
        history.setTemperature(temperature);
        history.setDescription(description);
        historyRepository.save(history);
    }
}
