package com.example.Eigar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Owner extends EigarUser {

    @OneToMany(mappedBy = "user")
    @JsonIgnore  // Add this annotation to break the infinite loop during serialization
    private List<Item> items;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<RentalTransaction> rentalTransactions;


}