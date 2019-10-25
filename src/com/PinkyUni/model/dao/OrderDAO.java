package com.PinkyUni.model.dao;

import com.PinkyUni.exceptions.DataSourceException;
import com.PinkyUni.model.entity.Order;
import java.util.List;

public interface OrderDAO {
    void addOrder(int tourId, String userPassport) throws DataSourceException;

    List<Order> getOrders() throws DataSourceException;

    List<Order> getByUser(String passport) throws DataSourceException;
}
