package com.mathildas.ecommerce.controller;

import com.mathildas.ecommerce.dto.AuthenticationRequest;
import com.mathildas.ecommerce.dto.ResponseBody;
import com.mathildas.ecommerce.dto.SignUpRequest;
import com.mathildas.ecommerce.dto.UserDTO;
import com.mathildas.ecommerce.entity.User;
import com.mathildas.ecommerce.repository.UserRepository;
import com.mathildas.ecommerce.services.auth.AuthService;
import com.mathildas.ecommerce.utils.Constants;
import com.mathildas.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

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

    private static final String HTTP_ACCESS_PATH = "/";
    private static final String HTTP_REFRESH_PATH = "/api/v1/auth/refresh-token";
    private static final String HTTP_SAME_SITE = "Lax";
    private static final int HTTP_COOKIE_MAX_AGE = 60 * 60 * 24; // 1 day
    private static final int HTTP_REFRESH_MAX_AGE = 60 * 60 * 24 * 7; // 7 days

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
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

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

            ResponseCookie accessCookie = ResponseCookie.from(Constants.HTTP_ACCESS_COOKIE, jwt)
                    .path(HTTP_ACCESS_PATH)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite(HTTP_SAME_SITE)
                    .maxAge(HTTP_COOKIE_MAX_AGE)
                    .build();

            ResponseCookie refreshCookie = ResponseCookie.from(Constants.HTTP_REFRESH_COOKIE, refreshToken)
                    .path(HTTP_REFRESH_PATH)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite(HTTP_SAME_SITE)
                    .maxAge(HTTP_REFRESH_MAX_AGE)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
            response.addHeader(HTTP_HEADER_EXPOSE_HEADERS, HTTP_HEADER_AUTHORIZATION);
            response.addHeader(HTTP_HEADER_ALLOW_HEADERS, HTTP_AUTH_HEADERS);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseBody<Object>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Stream.of(cookies)
                .filter(cookie -> Constants.HTTP_REFRESH_COOKIE.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue))
                .orElse(null);

        String username = jwtUtil.extractUserName(token);
        String newAccessToken = jwtUtil.generateToken(username);

        ResponseCookie accessCookie = ResponseCookie.from(Constants.HTTP_ACCESS_COOKIE, newAccessToken)
                .path(HTTP_ACCESS_PATH)
                .httpOnly(true)
                .secure(true)
                .sameSite(HTTP_SAME_SITE)
                .maxAge(HTTP_COOKIE_MAX_AGE)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseBody.builder()
                                .message("Access token refreshed successfully")
                                .statusCode(HttpStatus.OK.value())
                                .data(null)
                                .build()
                );
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from(HTTP_ACCESS_COOKIE, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from(HTTP_REFRESH_COOKIE, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
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
