package com.PinkyUni.controller;

import com.PinkyUni.exceptions.AlreadyExistsException;
import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.exceptions.NotEnoughDataException;
import com.PinkyUni.exceptions.NotFoundException;
import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.User;
import com.PinkyUni.model.service.*;
import com.PinkyUni.view.TourListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.PinkyUni.model.entity.Tour;

import java.util.*;

public class Controller {

    private UserService userService = ServiceFactory.getInstance().getUserService();
    private AgencyService agencyService = ServiceFactory.getInstance().getAgencyService();
    private ObservableList<Tour> items = FXCollections.observableArrayList();
    private static User currentUser;
    private static boolean isMine;

    @FXML
    private ComboBox<String> countryField;

    @FXML
    private DatePicker departureField;

    @FXML
    private DatePicker arrivalField;

    @FXML
    private ListView<Tour> tourList;

    @FXML
    private Button searchBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField passportField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button allToursBtn;

    @FXML
    private Button userToursBtn;

    @FXML
    private ComboBox<String> sortField;

    private boolean isDefaultSort = true;

    private Comparator<Tour> byPriceComparator = (o1, o2) -> (int) (o1.getPrice() - o2.getPrice());
    private Comparator<Tour> byNameComparator = Comparator.comparing(Tour::getName);

    private void showAllTours() {
        try {
            isMine = currentUser == null;
            items.clear();
            List<Tour> tours = agencyService.getTours();
            if (isDefaultSort)
                tours.sort(byNameComparator);
            else
                tours.sort(byPriceComparator);
            items.addAll(tours);
        } catch (DataSourceException e) {
            showAlert("Error", "Not Found", "Data source is not available!!!!!!!!!!");
        }
    }

    @FXML
    void initialize() {
        ObservableList<String> countryCodes = FXCollections.observableArrayList();
        for (Country.CountryCode countryCode : Country.CountryCode.values())
            countryCodes.add(countryCode.getName());
        countryField.setItems(countryCodes);

        ObservableList<String> sorts = FXCollections.observableArrayList();
        sorts.add("Name");
        sorts.add("Price");
        sortField.setItems(sorts);

        tourList.setItems(items);
        tourList.setCellFactory(param -> new TourListViewCell());
        showAllTours();

        sortField.valueProperty().addListener((ov, t, t1) -> {
            isDefaultSort = sortField.getValue().equals("Name");
            showAllTours();
        });

        allToursBtn.setOnMouseClicked(mouseEvent -> {
            showAllTours();
        });

        userToursBtn.setOnMouseClicked(mouseEvent -> {
            if (currentUser != null)
                try {
                    isMine = true;
                    items.clear();
                    items.addAll(agencyService.getUserTours(currentUser));
                } catch (DataSourceException | NotFoundException e) {
                    showAlert("Error", "Not Found", "Data source is not available!!!!!!!!!!");
                }
            else
                showAlert("Error", "Login firstly", "Please login to see booked tours.");
        });

        searchBtn.setOnMouseClicked(mouseEvent -> {
            try {
                if (countryField.getValue().isEmpty() || departureField.getValue() == null || arrivalField.getValue() == null)
                    throw new NotEnoughDataException("Fill all fields");
                List<Tour> tourList = agencyService.getByParams(countryField.getValue(), java.sql.Date.valueOf(departureField.getValue()),
                        java.sql.Date.valueOf(arrivalField.getValue()));
                items.clear();
                items.addAll(tourList);
            } catch (DataSourceException e) {
                showAlert("Error", "Not Found", "Data source is not available!!!!!!!!!!");
            } catch (NotEnoughDataException e) {
                showAlert("Error", "Wrong input", "Please fill all fields");
            }

        });

        loginBtn.setOnMouseClicked(mouseEvent -> {
            String name = nameField.getText();
            String password = HashService.getHash(passwordField.getText());
            try {
                currentUser = userService.singIn(name, password);
                showAllTours();
            } catch (NotEnoughDataException e) {
                showAlert("Error", "Wrong data", "Fill all fields!");
            } catch (NotFoundException e) {
                showAlert("Error", "Not Found", "User with such name not found!");
            } catch (DataSourceException e) {
                showAlert("Error", "Invalid Data Source", "Data source is not available!!!!!!!!!!");
            }
        });

        registerBtn.setOnMouseClicked(mouseEvent -> {
            String name = nameField.getText();
            String password = HashService.getHash(passwordField.getText());
            String passport = passportField.getText();
            String phone = phoneField.getText();
            try {
                currentUser = userService.register(name, password, passport, phone);
            } catch (NotEnoughDataException e) {
                showAlert("Error", "Wrong data", "Fill all fields!");
            } catch (NotFoundException e) {
                showAlert("Error", "Not Found", "User with such name not found!");
            } catch (DataSourceException e) {
                showAlert("Error", "Invalid Data Source", "Data source is not available!!!!!!!!!!");
            } catch (AlreadyExistsException e) {
                showAlert("Error", "Invalid data", "User with such name already exists.");
            }
        });
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean getIsMine() {
        return isMine;
    }
}