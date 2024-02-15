package com.example.Eigar.model;

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
    private List<Review> reviews;

}