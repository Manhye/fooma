package com.example.fooma.repository;

import com.example.fooma.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByLocation(String location);
    List<Food> findByExpirationDateBefore(LocalDate date);

    List<Food> findByCategoryId(Long categoryId);
    List<Food> findByCategoryIdAndStatus(Long categoryId, Food.Status status);

}
