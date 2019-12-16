package epam.entity;

import java.io.Serializable;
import java.util.Objects;

public class Hotel implements Serializable, Comparable<Hotel> {

    private int id;
    private String countryName;
    private int stars; //1-7
    private String name;
    private String webSite;
    private String description;
    private String address;

    public Hotel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return getStars() == hotel.getStars() &&
                getCountryName().equals(hotel.getCountryName()) &&
                Objects.equals(getName(), hotel.getName()) &&
                Objects.equals(getAddress(), hotel.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryName(), getStars(), getName(), getAddress());
    }

    @Override
    public int compareTo(Hotel o) {
        if (!name.equals(o.name))
            return name.compareTo(o.name);
        else
            return id - o.id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

}
