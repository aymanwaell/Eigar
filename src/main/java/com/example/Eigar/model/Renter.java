package com.example.Eigar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class Renter extends EigarUser {

    @OneToMany(mappedBy = "renter")
    @JsonManagedReference // Use this annotation to break the circular reference
    private List<RentalTransaction> rentalTransactions;

    @OneToMany(mappedBy = "renter")
    private List<Review> reviews;

}