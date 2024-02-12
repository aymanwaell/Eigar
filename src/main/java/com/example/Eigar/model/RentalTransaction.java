package com.example.Eigar.model;

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
    @JoinColumn(name = "user_id")
    private EigarUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
