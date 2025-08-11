package com.mathildas.ecommerce.services.admin.category;

import com.mathildas.ecommerce.dto.CategoryDTO;
import com.mathildas.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO updateCategory(CategoryDTO category);
    CategoryDTO getCategoryById(Long id);
    boolean deleteCategory(Long id);
    List<CategoryDTO> findAllCategories();
}
