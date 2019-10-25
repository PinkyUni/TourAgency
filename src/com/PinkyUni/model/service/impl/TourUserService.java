package com.PinkyUni.model.service.impl;

import com.PinkyUni.exceptions.AlreadyExistsException;
import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.exceptions.NotFoundException;
import com.PinkyUni.model.dao.*;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.entity.User;
import com.PinkyUni.exceptions.NotEnoughDataException;
import com.PinkyUni.model.service.UserService;

public class TourUserService implements UserService {

    private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
    private final OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

    @Override
    public User singIn(String name, String password) throws NotEnoughDataException, NotFoundException, DataSourceException {
        if (!name.isEmpty() && !password.isEmpty())
            if (userDAO.exists(name, password))
                return userDAO.getUserByName(name);
        throw new NotEnoughDataException("Fill all fields!");
    }

    @Override
    public User register(String name, String password, String passport, String phoneNumber) throws NotEnoughDataException, DataSourceException, AlreadyExistsException {
        if (!name.isEmpty() && !password.isEmpty() && !passport.isEmpty() && !phoneNumber.isEmpty()) {
            try {
                userDAO.getUserByName(name);
                throw new AlreadyExistsException("User " + name + " already exists.");
            } catch (NotFoundException e) {
                User user = new User(name, password, passport, phoneNumber);
                userDAO.addUser(user);
                return user;
            }
        }
        throw new NotEnoughDataException("Fill all fields!");
    }

    @Override
    public void bookTour(Tour tour, User user) throws DataSourceException {
        orderDAO.addOrder(tour.getId(), user.getPassport());
    }
}
