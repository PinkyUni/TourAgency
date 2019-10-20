package com.PinkyUni.model.dao;

public interface UserDAO {
    void signIn(String name, String password);
    void register(String name, String password);
    void bookTour(int id);
}
