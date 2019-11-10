package PinkyUni.entity;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable, Comparable<Country> {

    private String name;
    private String description;

    public Country() {
    }

    @Override
    public int compareTo(Country o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name.equals(((Country) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
