package com.example.dreamshop.service.user;

import com.example.dreamshop.dto.UserDto;
import com.example.dreamshop.exceptions.AlreadyExistException;
import com.example.dreamshop.exceptions.ResourceNotFoundException;
import com.example.dreamshop.model.User;
import com.example.dreamshop.repository.UserRepository;
import com.example.dreamshop.request.CreateUserRequest;
import com.example.dreamshop.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstname(req.getFirstname());
                    user.setLastname(passwordEncoder.encode(req.getPassword()));
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(()-> new AlreadyExistException("Oops " + request.getEmail()+ " User Already Exist"));
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest request) {

        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstname(request.getFirstname());
            existingUser.setLastname(request.getLastname());
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {

        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        ()->{ throw new ResourceNotFoundException("User not found");
                });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
