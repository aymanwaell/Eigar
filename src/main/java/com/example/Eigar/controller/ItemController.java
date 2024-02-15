package com.example.Eigar.controller;

import com.example.Eigar.exception.ItemNotFoundException;
import com.example.Eigar.exception.ItemServiceException;
import com.example.Eigar.exception.UserNotFoundException;
import com.example.Eigar.model.EigarUser;
import com.example.Eigar.model.Item;
import com.example.Eigar.model.Owner;
import com.example.Eigar.response.ItemResponse;
import com.example.Eigar.service.ItemService;
import com.example.Eigar.service.UserService;
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
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable long itemId) {
        try {
            Item theItem = itemService.getItemById(itemId);
            return ResponseEntity.ok(ItemResponse.success(theItem));
        } catch (ItemNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound("Item not found"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ItemResponse.error("Internal Server Error"));
        }
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ItemResponse> addNewItem(@RequestBody Item newItem, @PathVariable long userId) {
        try {
            // Check if the user is an Owner
            EigarUser user = userService.getUserById(userId);

            if (user instanceof Owner) {
                // If the user is an Owner, proceed with adding the item
                Item createdItem = itemService.addNewItem(newItem, userId);
                return ResponseEntity.ok(ItemResponse.success(createdItem));
            } else {
                // If the user is not an Owner (e.g., Renter), deny the request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ItemResponse.error("You cannot add an item as a Renter"));
            }
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ItemResponse.notFound(ex.getMessage()));
        } catch (ItemServiceException ex) {
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
