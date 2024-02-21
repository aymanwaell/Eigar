package com.example.Eigar.service;

import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.*;
import com.example.Eigar.Repository.RentalTransactionRepository;
import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalTransactionService {

    private final RentalTransactionRepository rentalTransactionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public RentalTransaction requestRentalTransaction(long itemId, long renterId)
            throws ItemNotFoundException, UserNotFoundException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        Renter renter = userRepository.findById(renterId)
                .map(user -> {
                    if (user instanceof Renter) {
                        return (Renter) user;
                    } else {
                        try {
                            throw new UserNotFoundException("User is not a Renter");
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .orElseThrow(() -> new UserNotFoundException("Renter not found"));

        BigDecimal itemPrice = item.getPrice(); // Assuming there is a getPrice() method in the Item class

        RentalTransaction newTransaction = new RentalTransaction();
        newTransaction.setItem(item);
        newTransaction.setRenter(renter);
        newTransaction.setPrice(itemPrice); // Set the item price to the RentalTransaction

        Owner owner = (Owner) item.getUser();
        newTransaction.setOwner(owner);

        newTransaction.setRentalStatus(RentalStatus.PENDING);

        RentalTransaction savedTransaction = rentalTransactionRepository.save(newTransaction);

        owner.getRentalTransactions().add(savedTransaction);
        userRepository.save(owner);

        return savedTransaction;
    }

    public List<RentalTransaction> getOwnerRequests(long ownerId) throws UserNotFoundException {
        Owner owner = userRepository.findById(ownerId)
                .map(user -> {
                    if (user instanceof Owner) {
                        return (Owner) user;
                    } else {
                        try {
                            throw new UserNotFoundException("User is not an Owner");
                        } catch (UserNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        return rentalTransactionRepository.findByOwnerAndRentalStatus(owner, RentalStatus.PENDING);
    }

    public RentalTransaction respondToRentalRequest(long transactionId, String response)
            throws RentalTransactionNotFoundException {
        try {
            RentalTransaction transaction = rentalTransactionRepository.findById(transactionId)
                    .orElseThrow(() -> new RentalTransactionNotFoundException("Rental Transaction not found"));

            if (response.equalsIgnoreCase("accept")) {
                transaction.setRentalStatus(RentalStatus.ACCEPTED);
                updateOwnerGains(transaction);
            } else if (response.equalsIgnoreCase("deny")) {
                transaction.setRentalStatus(RentalStatus.CANCELED);
            }

            return rentalTransactionRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void updateOwnerGains(RentalTransaction transaction) {
        Owner owner = transaction.getOwner();
        BigDecimal ownerGains = owner.getGains();

        if (ownerGains == null) {
            ownerGains = BigDecimal.ZERO;
        }

        BigDecimal transactionPrice = transaction.getPrice();
        ownerGains = ownerGains.add(transactionPrice);
        owner.setGains(ownerGains);
        userRepository.save(owner);
        System.out.println("Owner Gains updated. New total gains: " + ownerGains);
    }

    public List<RentalTransaction> getUserTransactions(long userId) throws UserNotFoundException {
        EigarUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user instanceof Owner) {
            return rentalTransactionRepository.findByOwner((Owner) user);
        } else if (user instanceof Renter) {
            return rentalTransactionRepository.findByRenter((Renter) user);
        } else {
            throw new IllegalArgumentException("User type not supported");
        }
    }
    public BigDecimal getOwnerGains(long ownerId) throws UserNotFoundException {
        Owner owner = userRepository.findById(ownerId)
                .filter(user -> user instanceof Owner)
                .map(user -> (Owner) user)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        return owner.getGains();
    }
}
