package com.mathildas.ecommerce.services.customer;

import com.mathildas.ecommerce.dto.ProductDTO;

import java.util.List;

public interface CustomerProductService {

    List<ProductDTO> getAllProducts();
}
