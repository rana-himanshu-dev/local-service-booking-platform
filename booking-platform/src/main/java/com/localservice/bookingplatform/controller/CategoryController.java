package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.ServiceCategoryRequest;
import com.localservice.bookingplatform.dto.ServiceCategoryResponse;
import com.localservice.bookingplatform.model.ServiceCategory;
import com.localservice.bookingplatform.repository.ServiceCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final ServiceCategoryRepository categoryRepository;

    public CategoryController(ServiceCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceCategoryResponse> createCategory(
            @RequestBody ServiceCategoryRequest request) {

        ServiceCategory category = new ServiceCategory();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIconUrl(request.getIconUrl());
        category.setIsActive(true);

        ServiceCategory saved = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToResponse(saved));
    }
    @GetMapping
    public ResponseEntity<List<ServiceCategoryResponse>> getAllCategories() {
        List<ServiceCategory> categories = categoryRepository.findAll()
                .stream()
                .filter(ServiceCategory::getIsActive)
                .collect(Collectors.toList());

        List<ServiceCategoryResponse> responses = categories.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponse> getCategoryById(@PathVariable Long id) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        return ResponseEntity.ok(convertToResponse(category));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ServiceCategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody ServiceCategoryRequest request) {

        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIconUrl(request.getIconUrl());

        ServiceCategory updated = categoryRepository.save(category);
        return ResponseEntity.ok(convertToResponse(updated));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        ServiceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        category.setIsActive(false);
        categoryRepository.save(category);
        return ResponseEntity.noContent().build();
    }

    private ServiceCategoryResponse convertToResponse(ServiceCategory category) {
        return new ServiceCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIconUrl(),
                category.getIsActive()
        );
    }
}