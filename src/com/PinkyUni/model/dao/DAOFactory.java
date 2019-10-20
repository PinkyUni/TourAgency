package com.PinkyUni.model.dao;

public class DAOFactory {

    private final DAOFactory daoFactory = new DAOFactory();

    private final TourDAO tourDAO = new XMLTourDAO();
    private final UserDAO userDAO = new XMLUserDAO();

    private DAOFactory(){}

    public DAOFactory getInstance() {
        return daoFactory;
    }

    public TourDAO getTourDAO() {
        return tourDAO;
    }

    public  UserDAO getUserDAO() {
        return userDAO;
    }
}
