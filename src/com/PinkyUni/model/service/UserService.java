package com.PinkyUni.model.service;

import com.PinkyUni.exceptions.AlreadyExistsException;
import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.exceptions.NotEnoughDataException;
import com.PinkyUni.exceptions.NotFoundException;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.entity.User;

public interface UserService {
    User singIn(String name, String password) throws NotEnoughDataException, NotFoundException, DataSourceException;
    User register(String name, String password, String passport, String phoneNumber) throws NotEnoughDataException, NotFoundException, DataSourceException, AlreadyExistsException;
    void bookTour(Tour tour, User user) throws DataSourceException;
}
