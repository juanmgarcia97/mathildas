package com.mathildas.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private double price;
    private String description;
    private byte[] imgBytes;
    private Long categoryId;
    private String categoryName;
    @JsonIgnore
    private MultipartFile file;
}
