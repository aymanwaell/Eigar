package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ItemService {

    private ItemRepository itemRepository;
}
