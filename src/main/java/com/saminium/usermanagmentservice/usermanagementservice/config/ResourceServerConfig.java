package com.saminium.usermanagmentservice.usermanagementservice.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.saminium.usermanagmentservice.usermanagementservice.exception.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.authentication.OidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.function.Function;

@EnableWebSecurity
public class ResourceServerConfig {


    private static final String SWAGGER_READ = "SCOPE_swagger.read";
    private static final String SWAGGER_WRITE = "SCOPE_swagger.write";
    private static final String SWAGGER_DELETE = "SCOPE_swagger.delete";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    private static final String[] AUTH_WHITELIST = {
            "isActive",
            "/index.html",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/swagger*/**",
            "/webjars/**",
            "favicon.ico",
            "/configuration/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/oauth/token",
            "/api/login",
            "/oauth2/authorize**",
            "/oauth2/authorize/**",
            "/oauth2/authorization/articles-client-oidc",
            "/oauth2/authorization/**",
            "/login/oauth2/**",
            "/login/oauth2**",
    };

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authz ->
                authz
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .antMatchers(HttpMethod.GET, "/api/**")
                        .hasAuthority(SWAGGER_READ)
                        .antMatchers(HttpMethod.POST, "/api/**")
                        .hasAuthority(SWAGGER_WRITE)
                        .antMatchers(HttpMethod.DELETE, "/api/**")
                        .hasAuthority(SWAGGER_DELETE)

                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer()
                .jwt(jwtConfigurer -> jwtConfigurer.decoder(JwtDecoders.fromIssuerLocation(issuer)));
        return http.build();
    }

}
