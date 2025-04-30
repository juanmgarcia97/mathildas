package com.mathildas.ecommerce.controller.admin;

import com.mathildas.ecommerce.dto.ProductDTO;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.services.admin.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseBody<ProductDTO>> addProduct(ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.createProduct(productDTO);
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
        List<ProductDTO> products = productService.getAllProducts();
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
        List<ProductDTO> products = productService.getAllProductsByName(name);
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
        List<ProductDTO> products = productService.getAllProductsByCategoryId(categoryId);
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
        ProductDTO product = productService.getProductById(id);
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
        ProductDTO updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseBody.<ProductDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Product updated successfully")
                        .data(updatedProduct)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<Void>> deleteProduct(@PathVariable Long id) {
        boolean isProductDeleted = productService.deleteProduct(id);
        if (isProductDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseBody.<Void>builder()
                            .statusCode(HttpStatus.OK.value())
                            .message("Product deleted successfully")
                            .data(null)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponseBody.<Void>builder()
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .message("Product with id " + id + " not found")
                            .data(null)
                            .build()
            );
        }
    }
}
