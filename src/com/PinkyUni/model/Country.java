package com.PinkyUni.model;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable, Comparable<Country> {

    public enum CountryCode {
        BLR("Belarus"),
        USA("USA"),
        UCR("UCR"),
        RUS("Russia"),
        UK("United Kingdom"),
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

    private CountryCode countryCode;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country() {
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
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
}
