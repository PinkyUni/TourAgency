package com.PinkyUni.model;

import java.util.Date;

public class Tour {

    private static int tourCount = 0;
    private int identifier;
    private String name;
    private Date departureTime;
    private Date arrivalTime;
    private TRANSPORT transport;
    private TOUR_TYPE type;
    private String description;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

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
        this.identifier = tourCount++;
        this.name = name;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.transport = transport;
        this.type = type;
        this.description = description;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
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
