package com.PinkyUni.model.dao;

import com.PinkyUni.model.entity.User;
import java.util.List;

public interface UserDAO {
    void addUser(User user) throws DataSourceException;
    User getUserByName(String name) throws NotFoundException, DataSourceException;
    List<User> getUsers() throws DataSourceException;
//    boolean exists(User user) throws NotFoundException;
    boolean exists(String name, String password) throws NotFoundException, DataSourceException;
}
