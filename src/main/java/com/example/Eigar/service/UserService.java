package com.example.Eigar.service;

import com.example.Eigar.Repository.OwnerRepository;
import com.example.Eigar.Repository.RenterRepository;
import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.exception.UserServiceException;
import com.example.Eigar.model.EigarUser;
import com.example.Eigar.model.Owner;
import com.example.Eigar.model.Renter;
import com.example.Eigar.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RenterRepository renterRepository;

    public EigarUser getUserById(Long userId) throws UserNotFoundException, UserServiceException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<EigarUser> getUsers() {
        return userRepository.findAll();
    }

    public EigarUser registerNewUser(EigarUser newUser, UserType userType) throws UserServiceException {
        try {
            // Set the user type before saving the user
            newUser.setUserType(userType);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UserServiceException("Error while registering new user", e);
        }
    }

    public Renter registerNewUser(Renter newRenter, UserType userType) throws UserServiceException {
        try {
            // Set the user type before saving the user
            newRenter.setUserType(userType);
            return renterRepository.save(newRenter);
        } catch (Exception e) {
            throw new UserServiceException("Error while registering new Renter user", e);
        }
    }

    public Owner registerNewUser(Owner newOwner, UserType userType) throws UserServiceException {
        try {
            // Set the user type before saving the user
            newOwner.setUserType(userType);
            return ownerRepository.save(newOwner);
        } catch (Exception e) {
            throw new UserServiceException("Error while registering new Owner user", e);
        }
    }

}

