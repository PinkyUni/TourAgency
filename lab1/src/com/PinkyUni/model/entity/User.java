package com.PinkyUni.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing User with fields id, name, passwordHash, passportId, phoneNumber
 * @author Anya
 * @version 1.0
 */
public class User implements Serializable, Comparable<User> {

    /** User id */
    private int id;
    /** User name */
    private String name;
    /** User password hash */
    private String passwordHash;
    /**User passport id */
    private String passport;
    /**User phone number */
    private String phoneNumber;

    /**
     * Constructor - creating a new user object
     * @see User#User(String, String, String, String)
     */
    public User() {
    }

    /**
     * Constructor - creating a new user object
     * @param name - user name
     * @param passwordHash - user password hash
     * @param passport - user password id
     * @param phoneNumber - user phone number
     * @see User#User()
     */
    public User(String name, String passwordHash, String passport, String phoneNumber) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.passport = passport;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Determine whether the objects are equal
     * @param o - another user
     * @return a negative integer, zero, or a positive integer as this User is less than, equal to, or greater than the specified User.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getPassport().equals(user.getPassport());
    }

    /**
     * Calculate hashcode
     * @return user hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPassport());
    }

    /**
     * Compare user to another user
     * @param o - another user
     * @return a negative integer, zero, or a positive integer as this User is less than, equal to, or greater than the specified User.
     * @see User
     */
    @Override
    public int compareTo(User o) {
        if (name.equals(o.name)) {
            return passport.compareTo(o.passport);
        } else
            return name.compareTo(o.name);
    }

    /**
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id - user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return user phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber - user phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return user name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name - user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return user password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     *
     * @param passwordHash - user password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     *
     * @return user passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     *
     * @param passport - user passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }
}

