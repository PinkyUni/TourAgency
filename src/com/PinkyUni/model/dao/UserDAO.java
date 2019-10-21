package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.User;

import java.util.ArrayList;

public interface UserDAO {
    void addUser(User user);
    ArrayList<User> getUsers();
    boolean exists(User user);
}
