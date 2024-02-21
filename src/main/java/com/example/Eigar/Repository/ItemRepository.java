package com.example.Eigar.Repository;

import com.example.Eigar.model.Item;
import com.example.Eigar.service.ItemService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
