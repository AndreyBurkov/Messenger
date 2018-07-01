package com.netcracker.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netcracker.jwt.domain.User;
import com.netcracker.jwt.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public boolean addUser(User user) {
        User u = userRepository.save(user);
        return (u == null ? false : true);
    }

    @Override
    public boolean updateUser(User user) {
        User u = userRepository.save(user);
        return (u == null ? false : true);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

}
