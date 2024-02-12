package com.example.Eigar.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    private String itemName;
    private String description;
    private ItemType itemType;
    private boolean availabilityStatus;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews;

    private RentalTransaction rentalTransaction;
}
