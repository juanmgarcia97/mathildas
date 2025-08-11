package com.mathildas.ecommerce.services.customer;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.mapper.ProductMapper;
import com.mathildas.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOs(productRepository.findAll());
    }

}
