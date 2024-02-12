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

public class EigarUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String Address;
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Item> items;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToOne(mappedBy = "user")
    private Identification identification;
}
