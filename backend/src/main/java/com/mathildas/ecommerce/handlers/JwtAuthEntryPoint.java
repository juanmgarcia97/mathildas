package com.mathildas.ecommerce.handlers;

import com.mathildas.ecommerce.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Throwable cause = authException.getCause();

        response.setContentType(Constants.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject body = new JSONObject();
        body.put("status", HttpStatus.UNAUTHORIZED.value());

        if(cause instanceof ExpiredJwtException) {
            body.put("message", Constants.TOKEN_EXPIRED_MESSAGE);
        } else if (cause instanceof BadCredentialsException) {
            body.put("message", Constants.BAD_CREDENTIALS_MESSAGE);
        } else {
            body.put("message", Constants.UNAUTHORIZED_MESSAGE);
        }

        response.getWriter().write(body.toString());
    }


}
