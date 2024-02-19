package com.example.Eigar.controller;

import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.RentalTransactionNotFoundException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.response.ItemResponse;
import com.example.Eigar.response.RentalTransactionResponse;
import com.example.Eigar.service.RentalTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<RentalTransactionResponse> getRentalTransactionById(@PathVariable long transactionId) {
        try {
            RentalTransaction transaction = rentalTransactionService.getRentalTransactionById(transactionId);
            return ResponseEntity.ok(RentalTransactionResponse.success(transaction));
        } catch (RentalTransactionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RentalTransactionResponse.notFound("Rental Transaction not found"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RentalTransactionResponse.error("Internal Server Error"));
        }
    }

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
    @GetMapping("/owner-requests/{ownerId}")
    public ResponseEntity<List<RentalTransaction>> getOwnerRequests(@PathVariable long ownerId) {
        try {
            List<RentalTransaction> ownerRequests = rentalTransactionService.getOwnerRequests(ownerId);
            return ResponseEntity.ok(ownerRequests);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.emptyList()); // or handle the error accordingly
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
}
