package com.example.Eigar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RentalTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionID;

    private LocalDateTime rentalStartDate;
    private LocalDateTime rentalEndDate;
    private RentalStatus rentalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    @JsonBackReference // Use this annotation to break the circular reference
    private Renter renter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}