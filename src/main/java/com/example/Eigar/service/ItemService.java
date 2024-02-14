package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemService {
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
