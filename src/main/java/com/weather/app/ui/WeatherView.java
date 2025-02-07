package com.weather.app.ui;

import com.weather.app.service.WeatherService;
import com.weather.app.model.WeatherHistory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route("")
public class WeatherView extends HorizontalLayout {

    private final WeatherService weatherService;

    public WeatherView(WeatherService weatherService) {
        this.weatherService = weatherService;

        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout historyLayout = new VerticalLayout();
        TextField latitudeField = new TextField("Широта");
        TextField longitudeField = new TextField("Долгота");
        Button showWeatherButton = new Button("Показать погоду");
        Label weatherLabel = new Label();
        Image weatherImage = new Image();

        showWeatherButton.addClickListener(event -> {
            try {
                double lat = Double.parseDouble(latitudeField.getValue());
                double lon = Double.parseDouble(longitudeField.getValue());

                String description = weatherService.getWeatherDescription(lat, lon);
                String descriptionMain = weatherService.getWeatherMain(lat, lon);
                String temperature = weatherService.getWeatherTemperature(lat, lon);

                weatherService.saveWeatherHistory(lat, lon, temperature, description);

                weatherLabel.setText("Температура: " + temperature + ", Описание: " + description);
                weatherImage.setSrc(getImagePath(descriptionMain.toLowerCase()));

                updateHistoryLayout(historyLayout);
            } catch (NumberFormatException e) {
                weatherLabel.setText("Ошибка: Введите корректные координаты");
            }
        });

        leftLayout.add(latitudeField, longitudeField, showWeatherButton, weatherLabel, weatherImage);

        leftLayout.addClassName("horizontal-layout");

        historyLayout.setWidth("50%");

        Label historyTitle = new Label("История");
        historyTitle.getStyle().set("font-weight", "bold").set("font-size", "20px");
        historyLayout.add(historyTitle);

        historyLayout.addClassName("history-layout");

        updateHistoryLayout(historyLayout);

        add(leftLayout, historyLayout);
    }

    private String getImagePath(String descriptionMain) {
        if (descriptionMain.contains("clear")) {
            return "/images/sunny.png";
        } else if (descriptionMain.contains("cloud")) {
            return "/images/cloudy.png";
        } else if (descriptionMain.contains("rain") || descriptionMain.contains("drizzle")) {
            return "/images/rainy.png";
        } else if (descriptionMain.contains("snow")) {
            return "/images/snowy.png";
        } else if (descriptionMain.contains("thunderstorm")) {
            return "/images/thunderstorm.png";
        } else if (descriptionMain.contains("mist") || descriptionMain.contains("fog")) {
            return "/images/misty.png";
        } else {
            return "/images/default.png";
        }
    }

    private void updateHistoryLayout(VerticalLayout historyLayout) {

        historyLayout.removeAll();

        Label historyTitle = new Label("История");
        historyTitle.getStyle().set("font-weight", "bold").set("font-size", "20px");
        historyLayout.add(historyTitle);

        List<WeatherHistory> historyList = weatherService.getAllHistory();

        for (WeatherHistory history : historyList) {
            String historyText = String.format(
                    "%s %s | Широта: %s | Долгота: %s | Ответ: %s",
                    history.getRequestDate(), history.getRequestTime(),
                    history.getLatitude(), history.getLongitude(),
                    history.getDescription()
            );
            Label historyLabel = new Label(historyText);
            historyLayout.add(historyLabel);
        }
    }
}
