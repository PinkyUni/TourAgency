package com.PinkyUni.model.service;

import com.PinkyUni.model.dao.DataSourceException;
import com.PinkyUni.model.dao.NotFoundException;
import com.PinkyUni.model.entity.Country;
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
