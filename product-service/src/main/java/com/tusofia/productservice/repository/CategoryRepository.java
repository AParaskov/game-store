package com.tusofia.productservice.repository;

import com.tusofia.productservice.domain.entity.Category;
import com.tusofia.productservice.domain.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(CategoryName categoryName);
}
