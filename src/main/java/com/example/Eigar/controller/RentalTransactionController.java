package com.example.Eigar.controller;


import com.example.Eigar.Repository.RentalTransactionRepository;
import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.Owner;
import com.example.Eigar.model.RentalStatus;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.response.RentalTransactionResponse;
import com.example.Eigar.service.RentalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rental-transaction")
@RequiredArgsConstructor
public class RentalTransactionController {

    private final RentalTransactionService rentalTransactionService;
    private final UserRepository userRepository;
    private final RentalTransactionRepository rentalTransactionRepository;

    @PostMapping("/request/{itemId}/{renterId}")
    public ResponseEntity<RentalTransactionResponse> requestRentalTransaction(
            @PathVariable long itemId,
            @PathVariable long renterId) {
        try {
            RentalTransaction transaction = rentalTransactionService.requestRentalTransaction(itemId, renterId);
            return ResponseEntity.ok(RentalTransactionResponse.success(transaction));
        } catch (ItemNotFoundException | UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RentalTransactionResponse.notFound(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RentalTransactionResponse.error("Internal Server Error"));
        }
    }
    @PostMapping("/respond/{transactionId}/{response}")
    public ResponseEntity<RentalTransactionResponse> respondToRentalRequest(
            @PathVariable long transactionId,
            @PathVariable String response) {
        try {
            RentalTransaction transaction = rentalTransactionService.respondToRentalRequest(transactionId, response);
            return ResponseEntity.ok(RentalTransactionResponse.success(transaction));
        } catch (RentalTransactionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RentalTransactionResponse.notFound(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RentalTransactionResponse.error("Internal Server Error"));
        }
    }

    @GetMapping("/owner-requests/{ownerId}")
    public ResponseEntity<List<RentalTransaction>> getOwnerRequests(@PathVariable long ownerId) {
        try {
            List<RentalTransaction> ownerRequests = rentalTransactionService.getOwnerRequests(ownerId);
            return ResponseEntity.ok(ownerRequests);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/owner-money/{ownerId}")
    public ResponseEntity<BigDecimal> getOwnerGains(@PathVariable long ownerId) {
        try {
            BigDecimal ownerGains = rentalTransactionService.getOwnerGains(ownerId);
            return ResponseEntity.ok(ownerGains);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BigDecimal.ZERO);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BigDecimal.ZERO);
        }
    }

    @GetMapping("/user-transactions/{userId}")
    public ResponseEntity<List<RentalTransaction>> getUserTransactions(@PathVariable long userId) {
        try {
            List<RentalTransaction> userTransactions = rentalTransactionService.getUserTransactions(userId);
            return ResponseEntity.ok(userTransactions);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}