package com.mathildas.ecommerce.services.admin.product;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.entity.Category;
import com.mathildas.ecommerce.entity.Product;
import com.mathildas.ecommerce.mapper.ProductMapper;
import com.mathildas.ecommerce.repository.CategoryRepository;
import com.mathildas.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productMapper.toEntity(productDTO);
        product.setCategory(category);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOs(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> getAllProductsByName(String name) {
        return productMapper.toDTOs(productRepository.findByNameContaining(name));
    }

    @Override
    public List<ProductDTO> getAllProductsByCategoryId(Long categoryId) {
        return productMapper.toDTOs(productRepository.findAllByCategoryId(categoryId));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        Category category = categoryRepository.findById(productDTO.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
//        product.setCategory(category);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productMapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
