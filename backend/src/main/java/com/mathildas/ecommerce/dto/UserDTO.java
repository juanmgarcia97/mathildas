package com.mathildas.ecommerce.dto;

import com.mathildas.ecommerce.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
