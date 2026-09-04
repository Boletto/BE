package com.demoboletto.security.filter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtFilterPublicPathTest {
    private final JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(null, null);
    private final JwtExceptionFilter exceptionFilter = new JwtExceptionFilter();

    @ParameterizedTest
    @ValueSource(strings = {
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/swagger-initializer.js",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/webjars/swagger-ui/index.css"
    })
    void swaggerPathsBypassJwtFilters(String requestUri) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", requestUri);

        assertTrue(authenticationFilter.shouldNotFilter(request));
        assertTrue(exceptionFilter.shouldNotFilter(request));
    }
}
