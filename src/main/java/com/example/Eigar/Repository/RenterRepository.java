package com.example.Eigar.Repository;

import com.example.Eigar.model.Owner;
import com.example.Eigar.model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter,Long> {
}
