package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.Tour;

import javax.xml.parsers.ParserConfigurationException;

public interface TourDAO {
    void addTour(Tour tour) throws ParserConfigurationException;
    void updateTour(int id, Tour tour);
    void deleteTour(int id);
    void getTours();
}
