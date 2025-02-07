package com.weather.app.ui;

import com.weather.app.model.WeatherHistory;
import com.weather.app.repository.WeatherHistoryRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("history")
public class HistoryView extends VerticalLayout {

    public HistoryView(WeatherHistoryRepository historyRepository) {

            Grid<WeatherHistory> grid = new Grid<>(WeatherHistory.class);

            grid.setItems(historyRepository.findAll());
            grid.setColumns("requestDate", "requestTime", "latitude", "longitude", "temperature", "description");

            add(grid);
    }
}
