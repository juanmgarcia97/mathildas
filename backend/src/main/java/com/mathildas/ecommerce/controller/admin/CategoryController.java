package com.mathildas.ecommerce.controller.admin;

import com.mathildas.ecommerce.dto.CategoryDTO;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.entity.Category;
import com.mathildas.ecommerce.services.admin.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseBody<CategoryDTO>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO category = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseBody.<CategoryDTO>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Category created successfully")
                        .data(category)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<List<CategoryDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Categories retrieved successfully")
                        .data(categories)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<CategoryDTO>> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<CategoryDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Category retrieved successfully")
                        .data(category)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody<CategoryDTO>> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<CategoryDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Category updated successfully")
                        .data(updatedCategory)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> deleteCategory(@PathVariable Long id) {
        boolean isCategoryDeleted = categoryService.deleteCategory(id);
        if(isCategoryDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseBody.<Void>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Category deleted successfully")
                            .data(null)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseBody.<Void>builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Category with id " + id + " not found")
                            .data(null)
                            .build()
            );
        }
    }
}
