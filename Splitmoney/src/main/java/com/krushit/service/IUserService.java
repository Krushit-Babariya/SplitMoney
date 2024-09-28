package com.krushit.service;

import com.krushit.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

    List<User> getAllUsers();
}
