package com.example.Eigar.service;

import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.exception.UserServiceException;
import com.example.Eigar.model.EigarUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public EigarUser getUserById(Long userId) throws UserNotFoundException, UserServiceException {
        try {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e) {
            throw e; // Re-throw UserNotFoundException as is
        } catch (Exception e) {
            throw new UserServiceException("Error while fetching user by ID", e);
        }
    }
}
