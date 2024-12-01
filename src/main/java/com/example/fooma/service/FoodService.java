package com.example.fooma.service;

import com.example.fooma.entity.Category;
import com.example.fooma.entity.Food;
import com.example.fooma.repository.CategoryRepository;
import com.example.fooma.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Food> getAvailableFoods() {
        return foodRepository.findAll().stream()
                .filter(food -> food.getStatus() == Food.Status.AVAILABLE)
                .collect(Collectors.toList());
    }

    public Food save(Food food) {
        return foodRepository.save(food);
    }

    public Food saveFood(Food food) {
        return foodRepository.save(food);  // 새로운 음식 추가
    }

    public List<Food> getFoodsByCategory(Long categoryId) {

        return foodRepository.findByCategoryId(categoryId); // 아래에서 설명
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Food> getFoodsByCategoryAndStatus(Long categoryId) {
        return foodRepository.findByCategoryIdAndStatus(categoryId, Food.Status.AVAILABLE);
    }

    public Food getFoodById(Long foodId) {
        return foodRepository.findById(foodId).orElse(null);
    }
}
