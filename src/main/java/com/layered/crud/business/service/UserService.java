package com.layered.crud.business.service;

import com.layered.crud.business.dto.UserCreateRequest;
import com.layered.crud.business.dto.UserUpdateRequest;
import com.layered.crud.business.dto.UserResponse;
import com.layered.crud.business.mapper.UserMapper;
import com.layered.crud.persistence.entity.UserEntity;
import com.layered.crud.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse create(UserCreateRequest request) {
        UserEntity entity = userMapper.createRequestToEntity(request);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToResponse(savedEntity);
    }

    public Optional<UserResponse> findById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::entityToResponse);
    }

    public Optional<UserResponse> changeById(Long userId, UserUpdateRequest request) {
        Optional<UserEntity> entityOptional = userRepository.findById(userId);
        
        if (entityOptional.isPresent()) {
            UserEntity entity = entityOptional.get();
            userMapper.partialUpdate(request, entity);
            UserEntity savedEntity = userRepository.save(entity);
            return Optional.of(userMapper.entityToResponse(savedEntity));
        }
        
        return Optional.empty();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
