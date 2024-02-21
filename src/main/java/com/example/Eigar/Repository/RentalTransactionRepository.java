package com.example.Eigar.Repository;

import com.example.Eigar.model.Owner;
import com.example.Eigar.model.RentalStatus;
import com.example.Eigar.model.RentalTransaction;
import com.example.Eigar.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalTransactionRepository extends JpaRepository<RentalTransaction,Long> {
    List<RentalTransaction> findByOwnerAndRentalStatus(Owner owner, RentalStatus rentalStatus);

    List<RentalTransaction> findByOwner(Owner user);

    List<RentalTransaction> findByRenter(Renter user);
}
