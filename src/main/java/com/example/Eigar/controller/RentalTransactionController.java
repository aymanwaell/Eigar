package com.example.Eigar.controller;

import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.response.ItemResponse;
import com.example.Eigar.response.RentalTransactionResponse;
import com.example.Eigar.service.RentalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rental-transaction")
@RequiredArgsConstructor

public class RentalTransactionController {

    private final RentalTransactionService rentalTransactionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRentalTransactions() {
        try {
            List<RentalTransaction> rentalTransactions = rentalTransactionService.getAllRentalTransactions();

            if (rentalTransactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No rental transactions found");
            }

            return ResponseEntity.ok(rentalTransactions);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<RentalTransactionResponse> getRentalTransactionById(@PathVariable long transactionId){
        try {
            RentalTransaction transaction = rentalTransactionService.getRentalTransactionById(transactionId);
            return ResponseEntity.ok(RentalTransactionResponse.success(transaction));
        } catch (RentalTransactionNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RentalTransactionResponse.notFound("Rental Transaction not found"));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RentalTransactionResponse.error("Internal Server Error"));
        }
    }
}
