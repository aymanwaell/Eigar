package com.example.Eigar.service;

import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.RentalStatus;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.model.Item;
import com.example.Eigar.model.Owner;
import com.example.Eigar.model.Renter;
import com.example.Eigar.Repository.RentalTransactionRepository;
import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalTransactionService {

    private final RentalTransactionRepository rentalTransactionRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<RentalTransaction> getAllRentalTransactions() {
        return rentalTransactionRepository.findAll();
    }

    public RentalTransaction getRentalTransactionById(long transactionId) throws RentalTransactionNotFoundException {
        return rentalTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RentalTransactionNotFoundException("Rental Transaction not found"));
    }

    public RentalTransaction requestRentalTransaction(long itemId, long renterId) throws ItemNotFoundException, UserNotFoundException {
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

        // Create a new transaction
        RentalTransaction newTransaction = new RentalTransaction();
        newTransaction.setItem(item);
        newTransaction.setRenter(renter);

        // Set the owner information
        Owner owner = (Owner) item.getUser(); // Assuming the Item has a 'getUser()' method
        newTransaction.setOwner(owner);

        // Set the initial status
        newTransaction.setRentalStatus(RentalStatus.PENDING);

        // Save the transaction
        RentalTransaction savedTransaction = rentalTransactionRepository.save(newTransaction);

        // Add the transaction to the owner's list of transactions
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

    public RentalTransaction respondToRentalRequest(long transactionId, String response) throws RentalTransactionNotFoundException {
        RentalTransaction transaction = rentalTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RentalTransactionNotFoundException("Rental Transaction not found"));

        if (response.equalsIgnoreCase("accept")) {
            transaction.setRentalStatus(RentalStatus.ACCEPTED);
        } else if (response.equalsIgnoreCase("deny")) {
            transaction.setRentalStatus(RentalStatus.CANCELED);
        }

        return rentalTransactionRepository.save(transaction);
    }
}
