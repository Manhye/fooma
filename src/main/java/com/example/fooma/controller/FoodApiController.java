package com.example.fooma.controller;

import com.example.fooma.entity.Food;
import com.example.fooma.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodApiController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAvailableFoods();
    }

    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return foodService.save(food);
    }
}
