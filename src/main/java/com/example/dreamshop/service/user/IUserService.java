package com.example.dreamshop.service.user;

import com.example.dreamshop.dto.UserDto;
import com.example.dreamshop.model.User;
import com.example.dreamshop.request.CreateUserRequest;
import com.example.dreamshop.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
