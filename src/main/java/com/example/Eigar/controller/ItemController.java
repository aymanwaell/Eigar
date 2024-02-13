package com.example.Eigar.controller;

import com.example.Eigar.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor

public class ItemController {

    private ItemRepository itemRepository;

    @GetMapping("/all")

}
