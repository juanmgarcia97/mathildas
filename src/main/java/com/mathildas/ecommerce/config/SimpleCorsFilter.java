package com.mathildas.ecommerce.config;

import com.mathildas.ecommerce.utils.Constants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@NoArgsConstructor
public class SimpleCorsFilter implements Filter {

    @Value("${app.client.url}")
    private String clientAppUrl;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Map<String, String> map = new HashMap<>();
        String originHeader = request.getHeader("origin");
        response.setHeader(Constants.HTTP_HEADER_ORIGIN, originHeader);
        response.setHeader(Constants.HTTP_HEADER_METHODS, Constants.ALLOWED_HTTP_METHODS);
        response.setHeader(Constants.HTTP_HEADER_MAX_AGE, Constants.MAX_AGE);
        response.setHeader(Constants.HTTP_HEADER_ALLOW_HEADERS, "*");

        if (Constants.OPTION_HEADER.equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
