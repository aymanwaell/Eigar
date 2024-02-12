package com.example.Eigar.Repository;

import com.example.Eigar.model.EigarUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EigarUser,Long> {
}
