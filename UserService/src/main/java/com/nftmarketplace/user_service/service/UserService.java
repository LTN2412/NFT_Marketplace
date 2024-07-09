package com.nftmarketplace.user_service.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nftmarketplace.user_service.model.User;
import com.nftmarketplace.user_service.model.dto.request.UserRequest;
import com.nftmarketplace.user_service.repository.UserRepository;
import com.nftmarketplace.user_service.utils.mapper.UserMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public User createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException();
        User user = UserMapper.INSTANCE.toUser(request);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setLastLogin(new Date());
        return userRepository.save(user);
    }

    public User getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return user;
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    public User updateUser(String id, UserRequest request) {
        User user = UserMapper.INSTANCE.toUSer(request,
                userRepository.findById(id).orElseThrow(() -> new RuntimeException()));
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public Void deleteUser(String id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException();
        userRepository.deleteById(id);
        return null;
    }

}
