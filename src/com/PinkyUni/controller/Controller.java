package com.PinkyUni.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.PinkyUni.view.TourListViewCell;
import com.PinkyUni.model.Tour;

public class Controller {

    @FXML
    private ListView<Tour> toursList;

    @FXML
    private Button searchBtn;

    @FXML
    void initialize() {
        ObservableList<Tour> items = FXCollections.observableArrayList();
        toursList.setItems(items);
        items.add(new Tour("The best tour ever", "C:\\Users\\Anya\\Pictures\\sticker\\img\\cat.jpg"));
        toursList.setCellFactory(param -> new TourListViewCell());

        searchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

    }

}