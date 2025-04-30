package com.mathildas.ecommerce.services.auth;

import com.mathildas.ecommerce.dto.SignUpRequest;
import com.mathildas.ecommerce.dto.UserDTO;
import com.mathildas.ecommerce.entity.User;
import com.mathildas.ecommerce.enums.UserRole;
import com.mathildas.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO createUser(SignUpRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(UserRole.CUSTOMER)
                .build();
        User createdUser = userRepository.save(user);

        return UserDTO.builder()
                .id(createdUser.getId())
                .email(createdUser.getEmail())
                .name(createdUser.getName())
                .userRole(createdUser.getRole())
                .build();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminUser() {
        User admin = userRepository.findByRole(UserRole.ADMIN);
        if (null == admin) {
            User user = User.builder()
                    .email("admin@yopmail.com")
                    .name("admin")
                    .role(UserRole.ADMIN)
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .build();
            userRepository.save(user);
        }
    }
}
