package com.PinkyUni.model.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Tour implements Serializable, Comparable<Tour> {

    private static int tourCount = 0;
    private int id;
    private String name;
    private Date departureTime;
    private Date arrivalTime;
    private TRANSPORT transport;
    private TOUR_TYPE type;
    private String description;
    private float price;
    private String image;
    private Country.CountryCode[] countryCodes;
    private String[] hotelsId;

    enum TRANSPORT {
        BUS, TRAIN, PLANE
    }

    enum TOUR_TYPE {
        SHOPPING, ACTIVE, TOURISM
    }

    public Tour() {
    }

    public Tour(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Tour(String name, Date departureTime, Date arrivalTime, TRANSPORT transport, TOUR_TYPE type, String description) {
        this.id = tourCount++;
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.transport = transport;
        this.type = type;
        this.description = description;
    }

    public Country.CountryCode[] getCountryCodes() {
        return countryCodes;
    }

    public void setCountryCodes(Country.CountryCode[] countryCodes) {
        this.countryCodes = countryCodes;
    }

    public String[] getHotelsId() {
        return hotelsId;
    }

    public void setHotelsId(String[] hotelsId) {
        this.hotelsId = hotelsId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int compareTo(Tour o) {
        if (!name.equals(o.name))
            return name.compareTo(o.name);
        else
            return id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Float.compare(tour.getPrice(), getPrice()) == 0 &&
                Objects.equals(getName(), tour.getName()) &&
                Objects.equals(getDepartureTime(), tour.getDepartureTime()) &&
                Objects.equals(getArrivalTime(), tour.getArrivalTime()) &&
                getTransport() == tour.getTransport() &&
                getType() == tour.getType() &&
                Arrays.equals(getCountryCodes(), tour.getCountryCodes()) &&
                Arrays.equals(getHotelsId(), tour.getHotelsId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getDepartureTime(), getArrivalTime(), getTransport(), getType(), getPrice());
        result = 31 * result + Arrays.hashCode(getCountryCodes());
        result = 31 * result + Arrays.hashCode(getHotelsId());
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public TRANSPORT getTransport() {
        return transport;
    }

    public void setTransport(TRANSPORT transport) {
        this.transport = transport;
    }

    public TOUR_TYPE getType() {
        return type;
    }

    public void setType(TOUR_TYPE type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
