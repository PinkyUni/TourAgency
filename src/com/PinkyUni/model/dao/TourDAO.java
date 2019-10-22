package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.Order;
import com.PinkyUni.model.entity.Tour;
import java.util.List;

public interface TourDAO {
    void addTour(Tour tour) throws DataSourceException;

    void updateTour(int id, Tour tour) throws NotFoundException, DataSourceException;

    void deleteTour(int id) throws NotFoundException, DataSourceException;

    List<Tour> getTours() throws DataSourceException;

    Tour getById(int id) throws DataSourceException, NotFoundException;

    List<Tour> getByUser(List<Order> orders) throws NotFoundException, DataSourceException;
}
