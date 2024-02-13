package com.example.Eigar.service;

import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.exception.UserServiceException;
import com.example.Eigar.model.EigarUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public EigarUser getUserById(Long userId) throws UserNotFoundException, UserServiceException {
        try {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserServiceException("Error while fetching user by ID", e);
        }
    }

    public List<EigarUser> getUsers() {
            return userRepository.findAll();
    }

    public EigarUser registerNewUser(EigarUser newUser) throws UserServiceException {
        try {
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UserServiceException("Error while registering new user", e);
        }
    }
}
