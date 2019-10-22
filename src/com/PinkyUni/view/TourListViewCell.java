package com.PinkyUni.view;

import com.PinkyUni.controller.Controller;
import com.PinkyUni.model.dao.DataSourceException;
import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.service.ServiceFactory;
import com.PinkyUni.model.service.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class TourListViewCell extends ListCell<Tour> {

    private UserService userService = ServiceFactory.getInstance().getUserService();

    public TourListViewCell() {
    }

    @Override
    public void updateItem(Tour tour, boolean empty) {
        super.updateItem(tour, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);

            HBox hBox = new HBox();
            File file = new File("src/com/sample/" + tour.getImage());
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView();
            imageView.setImage(image);

            imageView.setPreserveRatio(false);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            hBox.getChildren().add(imageView);

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(5.0, 10.0, 0.0, 10.0));

            Label name = new Label();
            name.setText(tour.getName());
            name.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            vBox.getChildren().add(name);

            Label country = new Label();
            StringBuilder countries = new StringBuilder();
            for(Country.CountryCode countryCode: tour.getCountryCodes())
                countries.append(countryCode.getName());
            country.setText("Country: " + countries);
            vBox.getChildren().add(country);

            Label price = new Label();
            price.setText("Price: " + tour.getPrice() + " euro");
            vBox.getChildren().add(price);

            Label departure = new Label();
            departure.setText("Departure: " + new SimpleDateFormat("dd.MM.yyyy").format(tour.getDepartureTime()));
            vBox.getChildren().add(departure);
            Label arrival = new Label();
            arrival.setText("Arrival: " + new SimpleDateFormat("dd.MM.yyyy").format(tour.getArrivalTime()));
            vBox.getChildren().add(arrival);

            if (!Controller.getIsMine() ) {
                Button bookBtn = new Button();
                bookBtn.setText("Book");
                bookBtn.setOnMouseClicked(mouseEvent -> {
                    try {
                        userService.bookTour(tour, Controller.getCurrentUser());
                    } catch (DataSourceException e) {

                    }
                });
                vBox.getChildren().add(bookBtn);
            }

            vBox.setSpacing(3.0);
            hBox.getChildren().add(vBox);
            setGraphic(hBox);
        }
    }
}
