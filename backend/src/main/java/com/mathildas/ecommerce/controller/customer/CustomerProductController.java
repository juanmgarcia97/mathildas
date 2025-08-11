package com.mathildas.ecommerce.controller.customer;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.services.customer.CustomerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/product")
public class CustomerProductController {

    @Autowired
    private CustomerProductService customerProductService;

    @GetMapping
    public ResponseEntity<ResponseBody<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = customerProductService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<List<ProductDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Products retrieved successfully")
                        .data(products)
                        .build()
        );
    }
}
