package com.tusofia.productservice.service.impl;

import com.tusofia.productservice.domain.entity.Category;
import com.tusofia.productservice.domain.enums.CategoryName;
import com.tusofia.productservice.repository.CategoryRepository;
import com.tusofia.productservice.service.CategoryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void initCategories() {
        if (categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        categoryRepository.save(Category.builder()
                                        .name(categoryName)
                                        .description("Description for " + categoryName.name())
                                        .build());
                    });
        }
    }
}
