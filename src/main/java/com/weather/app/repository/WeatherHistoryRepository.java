package com.weather.app.repository;

import com.weather.app.model.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, Long> {
}
