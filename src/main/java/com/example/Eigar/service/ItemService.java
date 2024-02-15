package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.ItemServiceException;
import com.example.Eigar.model.Item;
import com.example.Eigar.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<ItemResponse> deleteItemById(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound("Item not found"));
        }

        itemRepository.deleteById(itemId);
        return ResponseEntity.ok(ItemResponse.success("Item deleted successfully"));
    }
}
