package com.krushit.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krushit.entity.User;
import com.krushit.model.UserModel;
import com.krushit.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel userModel) throws IOException {
        User user = new User(); 
        BeanUtils.copyProperties(userModel, user); 
        
        String defaultPicturePath = "src/main/resources/static/images/avatar.webp";
        
        File pictureFile = new File(defaultPicturePath);
        byte[] pictureBytes = Files.readAllBytes(pictureFile.toPath());
        user.setPicture(pictureBytes);
        user.setCustomPicture(true); 
        user.setLocale("en");

        return userRepository.save(user);
    }
    
    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt; 
        }
        return Optional.empty(); 
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
