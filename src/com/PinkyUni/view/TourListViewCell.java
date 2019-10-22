package com.PinkyUni.view;

import com.PinkyUni.controller.Controller;
import com.PinkyUni.model.dao.DataSourceException;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.service.ServiceFactory;
import com.PinkyUni.model.service.UserService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.File;

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
            VBox vBox = new VBox();
            ImageView imageView = new ImageView();
            Label textField = new Label();
            File file = new File("src/com/sample/" + tour.getImage());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            setText(null);
            textField.setText(tour.getName());
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(textField);
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
            setGraphic(vBox);
        }
    }
}
