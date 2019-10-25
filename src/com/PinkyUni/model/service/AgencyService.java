package com.PinkyUni.model.service;

import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.exceptions.NotEnoughDataException;
import com.PinkyUni.exceptions.NotFoundException;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.entity.User;

import java.util.Date;
import java.util.List;

public interface AgencyService {
    void addTour(Tour tour) throws NotFoundException, DataSourceException;

    void deleteTour(int id) throws NotFoundException, DataSourceException;

    List<Tour> getTours() throws DataSourceException;

    List<Tour> getUserTours(User user) throws DataSourceException, NotFoundException;

    List<Tour> getByParams(String countryName, Date departure, Date arrival) throws DataSourceException, NotEnoughDataException;
}
