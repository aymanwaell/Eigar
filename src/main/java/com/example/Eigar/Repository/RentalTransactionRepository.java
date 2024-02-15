package com.example.Eigar.Repository;

import com.example.Eigar.model.RentalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalTransactionRepository extends JpaRepository<RentalTransaction,Long> {
}
