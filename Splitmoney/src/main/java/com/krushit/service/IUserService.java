package com.krushit.service;

import com.krushit.entity.User;
import com.krushit.model.UserModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(UserModel userModel) throws IOException;
    
    Optional<User> loginUser(String email, String password);

    User updateUser(User user);

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);

    List<User> getAllUsers();
}
