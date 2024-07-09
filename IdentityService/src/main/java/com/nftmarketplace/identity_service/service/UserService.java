package com.nftmarketplace.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nftmarketplace.identity_service.dto.request.UserCreationRequest;
import com.nftmarketplace.identity_service.exception.AppException;
import com.nftmarketplace.identity_service.exception.ErrorCode;
import com.nftmarketplace.identity_service.mapper.UserMapper;
import com.nftmarketplace.identity_service.model.Role;
import com.nftmarketplace.identity_service.model.User;
import com.nftmarketplace.identity_service.repository.RoleRepository;
import com.nftmarketplace.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.EXISTED);
        User user = UserMapper.INSTANCE.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }
        return userRepository.save(user);
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTED));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UserCreationRequest request) {
        if (!userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.NOT_EXISTED);
        User user = UserMapper.INSTANCE.toUser(request);
        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userRepository.save(user);
    }

    public Void deleteUser(String id) {
        if (!userRepository.existsById(id))
            throw new AppException(ErrorCode.NOT_EXISTED);
        userRepository.deleteById(id);
        return null;
    }
}
