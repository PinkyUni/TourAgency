package com.PinkyUni.model.dao;

public class DAOFactory {

    private static final DAOFactory daoFactory = new DAOFactory();

    private final TourDAO tourDAO = new XMLTourDAO();
    private final UserDAO userDAO = new XMLUserDAO();
    private final OrderDAO orderDAO = new XMLOrderDAO();

    private DAOFactory(){}

    public static DAOFactory getInstance() {
        return daoFactory;
    }

    public TourDAO getTourDAO() {
        return tourDAO;
    }

    public  UserDAO getUserDAO() {
        return userDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }
}
