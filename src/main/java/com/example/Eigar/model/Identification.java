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

public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private IdType idType;
    private int idNumber;
    private LocalDateTime ExpiryDate;
    private LocalDateTime uploadDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EigarUser user;
}
