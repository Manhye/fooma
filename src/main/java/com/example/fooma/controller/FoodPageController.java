package com.example.fooma.controller;

import com.example.fooma.entity.Food;
import com.example.fooma.entity.Category;
import com.example.fooma.entity.User;
import com.example.fooma.service.FoodService;
import com.example.fooma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FoodPageController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html 렌더링
    }

    @GetMapping("/food/add")
    public String showAddFoodForm(Model model) {
        // 카테고리 목록을 화면에 전달
        List<Category> categories = foodService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("food", new Food()); // 빈 Food 객체를 모델에 추가
        return "food-add";
    }

    @PostMapping("/food/add")
    public String addFood(@ModelAttribute Food food, Model model) {
        food.setStatus(Food.Status.AVAILABLE);  // 기본적으로 상태는 AVAILABLE로 설정
        foodService.saveFood(food);  // 음식 저장
        model.addAttribute("message", "Food added successfully!");
        return "redirect:/foods";  // 음식 목록 페이지로 리다이렉트
    }

    @GetMapping("/foods")
    public String foodList(@RequestParam(value = "category", required = false) Long categoryId, Model model) {
        List<Food> foods;
        if (categoryId != null) {
            // 카테고리가 선택되었으면, 해당 카테고리에 속한 음식만 필터링
            foods = foodService.getFoodsByCategoryAndStatus(categoryId);
        } else {
            // 카테고리가 선택되지 않았으면, 전체 음식 리스트
            foods = foodService.getAvailableFoods();
        }
        // 모든 카테고리 목록을 가져와서 화면에 전달
        List<Category> categories = foodService.getAllCategories();

        // 모델에 음식 목록과 카테고리 목록을 추가하여 화면에 전달
        model.addAttribute("foods", foods);
        model.addAttribute("categories", categories);

        return "food-list"; // food-list.html로 전달
    }

    // 음식 상태 변경을 위한 이메일 입력 폼
    @GetMapping("/food/{id}/take")
    public String showTakeFoodForm(@PathVariable("id") Long foodId, Model model) {
        Food food = foodService.getFoodById(foodId);
        model.addAttribute("food", food);
        return "take-food";  // 확인 창이 포함된 폼
    }


    // 음식 상태 업데이트 (이메일과 이름 입력 후)
    @PostMapping("/food/{id}/take")
    public String takeFood(@PathVariable("id") Long foodId,
                           @RequestParam("email") String email,
                           @RequestParam("name") String name,
                           Model model) {
        Food food = foodService.getFoodById(foodId);

        // 이메일과 이름을 통해 사용자 찾기 또는 새로 추가
        UserService userService = new UserService();
        User user = userService.findOrCreateUserByEmailAndName(email, name);

        // 음식 상태를 TAKEN으로 변경
        food.setStatus(Food.Status.TAKEN);
        foodService.saveFood(food);

        model.addAttribute("message", "Food taken successfully!");
        return "redirect:/foods";  // 음식 목록 페이지로 리다이렉트
    }
}



