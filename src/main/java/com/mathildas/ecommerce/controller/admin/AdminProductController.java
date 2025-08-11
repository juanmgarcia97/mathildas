package com.mathildas.ecommerce.controller.admin;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.services.admin.product.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @PostMapping
    public ResponseEntity<ResponseBody<ProductDTO>> addProduct(ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = adminProductService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ResponseBody.<ProductDTO>builder()
                            .statusCode(HttpStatus.CREATED.value())
                            .message("Product created successfully")
                            .data(createdProduct)
                            .build()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseBody.<ProductDTO>builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Failed to retrieve category from product: " + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = adminProductService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<List<ProductDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Products retrieved successfully")
                        .data(products)
                        .build()
        );
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ResponseBody<List<ProductDTO>>> getAllProductsByName(@PathVariable String name) {
        List<ProductDTO> products = adminProductService.getAllProductsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<List<ProductDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Products retrieved successfully")
                        .data(products)
                        .build()
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseBody<List<ProductDTO>>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> products = adminProductService.getAllProductsByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<List<ProductDTO>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Products retrieved successfully")
                        .data(products)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = adminProductService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<ProductDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Product retrieved successfully")
                        .data(product)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody<ProductDTO>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        ProductDTO updatedProduct = adminProductService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<ProductDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Product updated successfully")
                        .data(updatedProduct)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Boolean>> deleteProduct(@PathVariable Long id) {
        boolean isProductDeleted = adminProductService.deleteProduct(id);
        if (isProductDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseBody.<Boolean>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Product deleted successfully")
                            .data(Boolean.TRUE)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseBody.<Boolean>builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Product with id " + id + " not found")
                            .data(Boolean.FALSE)
                            .build()
            );
        }
    }
}
