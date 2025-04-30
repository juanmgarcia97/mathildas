package com.mathildas.ecommerce.filters;

import com.mathildas.ecommerce.services.jwt.UserDetailsServiceImpl;
import com.mathildas.ecommerce.utils.Constants;
import com.mathildas.ecommerce.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(Constants.HTTP_HEADER_AUTHORIZATION);
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {
            token = authHeader.substring(Constants.INDEX_TOKEN_EXTRACTION);
            try {
                userName = jwtUtil.extractUserName(token);
            } catch (ExpiredJwtException e) {
                logger.error("JWT expired", e);
//                response.getWriter().write(
//                        new JSONObject()
//                                .put("data", JSONObject.NULL)
//                                .put("message", e.getMessage())
//                                .put("statusCode", HttpStatus.FORBIDDEN.value())
//                                .toString()
//                );
            } catch (Exception e) {
                logger.error("Error extracting username from token: {}", e);
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}