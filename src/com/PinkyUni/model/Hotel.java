package com.PinkyUni.model;

import java.io.Serializable;
import java.util.Objects;

public class Hotel implements Serializable, Comparable<Hotel> {

    private int id;
    private Country.CountryCode countryCode;
    private int stars; //1-7
    private String name;
    private String webSite;
    private String description;
    private String address;
    private FOOD food;

    enum FOOD {
        ALL_INCLUSIVE, BREAKFAST, NONE
    }

    public FOOD getFood() {
        return food;
    }

    public void setFood(FOOD food) {
        this.food = food;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hotel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country.CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Country.CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return getStars() == hotel.getStars() &&
                getCountryCode() == hotel.getCountryCode() &&
                Objects.equals(getName(), hotel.getName()) &&
                Objects.equals(getAddress(), hotel.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getStars(), getName(), getAddress());
    }

    @Override
    public int compareTo(Hotel o) {
        if (!name.equals(o.name))
            return name.compareTo(o.name);
        else
            return id - o.id;
    }
}
