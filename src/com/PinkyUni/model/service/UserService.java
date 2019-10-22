package com.PinkyUni.model.service;

import com.PinkyUni.model.dao.AlreadyExistsException;
import com.PinkyUni.model.dao.DataSourceException;
import com.PinkyUni.model.dao.NotFoundException;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.entity.User;

public interface UserService {
    User singIn(String name, String password) throws NotEnoughDataException, NotFoundException, DataSourceException;
    User register(String name, String password, String passport, String phoneNumber) throws NotEnoughDataException, NotFoundException, DataSourceException, AlreadyExistsException;
    void bookTour(Tour tour, User user) throws DataSourceException;
}
