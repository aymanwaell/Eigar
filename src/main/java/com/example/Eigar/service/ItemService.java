package com.example.Eigar.service;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.Repository.UserRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.ItemServiceException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.EigarUser;
import com.example.Eigar.model.Item;
import com.example.Eigar.model.Owner;
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
    private final UserRepository userRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(long itemId) throws ItemNotFoundException, ItemServiceException{
            return itemRepository.findById(itemId)
                    .orElseThrow(()-> new ItemNotFoundException("Item not found"));
    }

    public Item addNewItem(Item newItem, long userId) throws UserNotFoundException {
        EigarUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Owner not found"));

        if (user == null) {
            // Handle the case where owner is null (optional)
            throw new UserNotFoundException("Owner not found");
        }

        newItem.setUser(user);

        return itemRepository.save(newItem);
    }

    public ResponseEntity<ItemResponse> deleteItemById(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound("Item not found"));
        }

        itemRepository.deleteById(itemId);
        return ResponseEntity.ok(ItemResponse.success("Item deleted successfully"));
    }
}
