package com.PinkyUni.model;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable, Comparable<Order> {

    private int id;
    private String userPassport;
    private int tourId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getTourId() == order.getTourId() &&
                Objects.equals(getUserPassport(), order.getUserPassport());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserPassport(), getTourId());
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserPassport() {
        return userPassport;
    }

    public void setUserPassport(String userPassport) {
        this.userPassport = userPassport;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    @Override
    public int compareTo(Order o) {
        return id - o.id;
    }
}
