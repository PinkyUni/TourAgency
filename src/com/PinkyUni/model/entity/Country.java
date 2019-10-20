package com.PinkyUni.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable, Comparable<Country> {

    private CountryCode countryCode;
    private String description;

    public enum CountryCode {
        BLR("Belarus"),
        USA("USA"),
        UCR("UCR"),
        RUS("Russia"),
        UK("United Kingdom"),
        NO("Norway"),
        ITL("Italy");

        //...

        private String name;

        CountryCode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Country() {
    }

    @Override
    public int compareTo(Country o) {
        return countryCode.getName().compareTo(o.countryCode.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getCountryCode() == country.getCountryCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}
