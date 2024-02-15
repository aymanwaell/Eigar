package com.example.Eigar.controller;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.ItemServiceException;
import com.example.Eigar.model.Item;
import com.example.Eigar.response.ItemResponse;
import com.example.Eigar.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor

public class ItemController {

    private final ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems()
    {
        return ResponseEntity.ok(itemService.getAllItems());
    }
    @GetMapping("/{itemId}")
        public ResponseEntity<ItemResponse>getItemById(@PathVariable long itemId){
        try {
            Item theItem = itemService.getItemById(itemId);
            return ResponseEntity.ok(ItemResponse.success(theItem));
        } catch (ItemNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound("Item not found"));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ItemResponse.error("Internal Server Error"));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody Item newItem){
        try {
            Item createdItem = itemService.addNewItem(newItem);
            return ResponseEntity.ok(ItemResponse.success(createdItem));
        } catch (ItemServiceException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ItemResponse.error("Internal Server Error"));
        }
    }
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ItemResponse> deleteItemById(@PathVariable long itemId) {
        try {
            itemService.deleteItemById(itemId);
            return ResponseEntity.ok(ItemResponse.success("Item deleted successfully"));
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound("Item not found"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ItemResponse.error("Internal Server Error"));
        }
    }
}
