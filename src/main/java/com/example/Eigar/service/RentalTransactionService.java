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
}

