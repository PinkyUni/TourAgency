package com.PinkyUni.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable, Comparable<User> {

    private int id;
    private String name;
    private String passwordHash;
    private String passport;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public User(String name, String passwordHash, String passport, String phoneNumber) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getPassport().equals(user.getPassport());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassport());
    }

    @Override
    public int compareTo(User o) {
        if (name.equals(o.name)) {
            return passport.compareTo(o.passport);
        } else
            return name.compareTo(o.name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}

