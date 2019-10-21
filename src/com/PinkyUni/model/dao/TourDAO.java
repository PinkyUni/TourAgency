package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.Tour;

import java.util.ArrayList;

public interface TourDAO {
    void addTour(Tour tour);
    void updateTour(int id, Tour tour);
    void deleteTour(int id);
    ArrayList<Tour> getTours();
}
