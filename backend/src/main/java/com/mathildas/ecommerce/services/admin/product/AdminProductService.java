package com.mathildas.ecommerce.services.admin.product;

import com.mathildas.ecommerce.dto.ProductDTO;

import java.util.List;

public interface AdminProductService {

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO productDTO);
    ProductDTO getProductById(Long id);
    boolean deleteProduct(Long id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProductsByName(String name);
    List<ProductDTO> getAllProductsByCategoryId(Long categoryId);
}
