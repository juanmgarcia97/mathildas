package com.mathildas.ecommerce.controller;

import com.mathildas.ecommerce.dto.AuthenticationRequest;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.dto.SignUpRequest;
import com.mathildas.ecommerce.dto.UserDTO;
import com.mathildas.ecommerce.entity.User;
import com.mathildas.ecommerce.repository.UserRepository;
import com.mathildas.ecommerce.services.auth.AuthService;
import com.mathildas.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static com.mathildas.ecommerce.utils.Constants.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest request, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(
                    new JSONObject()
                            .put("data", new JSONObject()
                                    .put("id", optionalUser.get().getId())
                                    .put("role", optionalUser.get().getRole()))
                            .put("message", "User logged in successfully")
                            .put("statusCode", HttpStatus.OK.value())
                            .toString()
            );

            response.addHeader(HTTP_HEADER_EXPOSE_HEADERS, HTTP_HEADER_AUTHORIZATION);
            response.addHeader(HTTP_HEADER_ALLOW_HEADERS, HTTP_AUTH_HEADERS);
            response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBody<UserDTO>> signUpUser(@RequestBody SignUpRequest request) {
        if (authService.hasUserWithEmail(request.getEmail())) {
            return new ResponseEntity<>(
                    ResponseBody.<UserDTO>builder()
                            .statusCode(HttpStatus.CONFLICT.value())
                            .message("User with this email already exists")
                            .build(),
                    HttpStatus.CONFLICT
            );
        }
        UserDTO userDTO = authService.createUser(request);
        return new ResponseEntity<>(
                ResponseBody.<UserDTO>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("User created successfully")
                        .data(userDTO)
                        .build(),
                HttpStatus.CREATED
        );
    }
}
