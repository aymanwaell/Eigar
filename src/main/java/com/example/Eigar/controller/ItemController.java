package com.example.Eigar.controller;

import com.example.Eigar.Repository.ItemRepository;
import com.example.Eigar.model.Item;
import com.example.Eigar.response.ItemResponse;
import com.example.Eigar.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor

public class ItemController {

    private ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems(){
        return new ResponseEntity<>(itemService.getAllItems(), HttpStatus.FOUND);
    }
}
