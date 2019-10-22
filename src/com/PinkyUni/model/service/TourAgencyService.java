package com.PinkyUni.model.service;

import com.PinkyUni.model.dao.*;
import com.PinkyUni.model.entity.Country;
import com.PinkyUni.model.entity.Tour;
import com.PinkyUni.model.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TourAgencyService implements AgencyService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final TourDAO tourDAO = daoFactory.getTourDAO();
    private final OrderDAO orderDAO = daoFactory.getOrderDAO();

    @Override
    public void addTour(Tour tour) throws DataSourceException {
        tourDAO.addTour(tour);
    }

    @Override
    public void deleteTour(int id) throws NotFoundException, DataSourceException {
        tourDAO.deleteTour(id);
    }

    @Override
    public List<Tour> getTours() throws DataSourceException {
        return tourDAO.getTours();
    }

    @Override
    public List<Tour> getUserTours(User user) throws DataSourceException, NotFoundException {
        return tourDAO.getByUser(orderDAO.getByUser(user.getPassport()));
    }

    @Override
    public List<Tour> getByParams(String countryName, Date departure, Date arrival) throws DataSourceException, NotEnoughDataException {
        List<Tour> tours = tourDAO.getTours();
        List<Tour> result = new ArrayList<>();
        for (Tour tour : tours) {
            if ((Arrays.stream(tour.getCountryCodes()).anyMatch(countryCode -> countryCode.getName().equals(countryName)))
                    && (tour.getDepartureTime().after(departure)) && (tour.getArrivalTime().before(arrival))) {
                result.add(tour);
            }
        }
        return result;
    }

}
