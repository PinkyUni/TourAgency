package com.PinkyUni.view;

import com.PinkyUni.model.entity.Tour;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.File;

public class TourListViewCell extends ListCell<Tour> {

    private VBox vBox = new VBox();

    public TourListViewCell() {
    }

    @Override
    public void updateItem(Tour tour, boolean empty) {
        super.updateItem(tour, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            ImageView imageView = new ImageView();
            Label textField = new Label();
            File file = new File("src/com/sample/" + tour.getImage());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            setText(null);
            textField.setText(tour.getName());
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(textField);
            setGraphic(vBox);
        }
    }
}
