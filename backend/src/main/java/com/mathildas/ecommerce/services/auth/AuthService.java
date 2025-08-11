package com.mathildas.ecommerce.services.auth;

import com.mathildas.ecommerce.dto.SignUpRequest;
import com.mathildas.ecommerce.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignUpRequest request);

    boolean hasUserWithEmail(String email);
}
