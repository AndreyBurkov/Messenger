package com.netcracker.jwt.service;


import com.netcracker.jwt.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    boolean addUser(User user);
    boolean updateUser(User user);
    void deleteUser(Long id);
}
