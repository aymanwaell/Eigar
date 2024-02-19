package com.example.Eigar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"items", "rentalTransactions"})
public class Owner extends EigarUser {

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Item> items;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<RentalTransaction> rentalTransactions;


}