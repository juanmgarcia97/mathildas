package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.CategoryDTO;
import com.mathildas.ecommerce.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toDTOs(List<Category> categories);
}
