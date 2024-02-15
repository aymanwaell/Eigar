package com.example.Eigar.service;

import com.example.Eigar.Repository.RentalTransactionRepository;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.model.RentalTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalTransactionService {

    private final RentalTransactionRepository rentalTransactionRepository;

    public List<RentalTransaction> getAllRentalTransactions() {
        return rentalTransactionRepository.findAll();
    }

    public RentalTransaction getRentalTransactionById(long transactionId) throws RentalTransactionNotFoundException {
        return rentalTransactionRepository.findById(transactionId)
                .orElseThrow(()-> new RentalTransactionNotFoundException("Rental Transaction is not found"));
    }
}
