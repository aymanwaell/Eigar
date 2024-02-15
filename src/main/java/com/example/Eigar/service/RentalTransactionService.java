package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.Repository.RentalTransactionRepository;
import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.Item;
import com.example.Eigar.model.RentalStatus;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.model.Renter;
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
                .orElseThrow(()-> new RentalTransactionNotFoundException("Rental Transaction is not found"));
    }
    public RentalTransaction requestRentalTransaction(long itemId, long renterId) throws ItemNotFoundException, UserNotFoundException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        Renter renter = (Renter) userRepository.findById(renterId)
                .orElseThrow(() -> new UserNotFoundException("Renter not found"));

        RentalTransaction transaction = new RentalTransaction();
        transaction.setRentalStatus(RentalStatus.PENDING);
        transaction.setItem(item);
        transaction.setRenter(renter);

        return rentalTransactionRepository.save(transaction);
    }

    public RentalTransaction respondToRentalRequest(long transactionId, String response) throws RentalTransactionNotFoundException {
        RentalTransaction transaction = rentalTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RentalTransactionNotFoundException("Rental transaction not found"));

        if (response.equalsIgnoreCase("accept")) {
            transaction.setRentalStatus(RentalStatus.ACCEPTED);
        } else if (response.equalsIgnoreCase("cancel")) {
            transaction.setRentalStatus(RentalStatus.CANCELED);
        } else {
            throw new IllegalArgumentException("Invalid response");
        }

        return rentalTransactionRepository.save(transaction);
    }
}

