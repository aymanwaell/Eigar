package com.example.Eigar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"rentalTransactions", "reviews"})
public class Renter extends EigarUser {

    @OneToMany(mappedBy = "renter")
    @JsonBackReference
    private List<RentalTransaction> rentalTransactions;

    @OneToMany(mappedBy = "renter")
    private List<Review> reviews;

}