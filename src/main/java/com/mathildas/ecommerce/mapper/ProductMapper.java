package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public abstract class ProductMapper {

    @Mapping(target = "imgBytes", source = "img")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "file", ignore = true) // Ignore the file field in DTO
    public abstract ProductDTO toDTO(Product product);

    @Mapping(target = "img", source = "file", qualifiedByName = "multipartToBytes")
    @Mapping(target = "category", ignore = true) // You'll set it manually after fetching
    public abstract Product toEntity(ProductDTO dto);

    public abstract List<ProductDTO> toDTOs(List<Product> products);

    @Named("multipartToBytes")
    protected byte[] multipartToBytes(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read bytes from MultipartFile", e);
        }
    }
}
