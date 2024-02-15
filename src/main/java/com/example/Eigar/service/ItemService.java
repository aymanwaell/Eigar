package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.ItemServiceException;
import com.example.Eigar.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(long itemId) throws ItemNotFoundException, ItemServiceException{
            return itemRepository.findById(itemId)
                    .orElseThrow(()-> new ItemNotFoundException("Item not found"));
    }

    public Item addNewItem(Item newItem) throws ItemServiceException{
        try {
            return itemRepository.save(newItem);
        } catch (Exception ex){
            throw new ItemServiceException("Error while registering new item", ex);
        }
    }
}
