package com.saminium.usermanagmentservice.usermanagementservice.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {



    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

                .components(new Components()
                        .addSecuritySchemes("saminium-auth", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Oauth2 flow")
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("http://localhost:8282/oauth2/token")
                                                .scopes(new Scopes()
                                                        .addString("swagger.read",
                                                                "for read operations")
                                                        .addString("swagger.write",
                                                                "for write operations"))))
                                .bearerFormat("JWT")
                                .scheme("bearer")))
                .security(Arrays.asList(
                        new SecurityRequirement().addList("saminium-auth")))
                .info(new Info()
                        .title("Saminium Web services")
                        .description("User service api.")
                        .termsOfService("terms")
                        .contact(new Contact().email("dev@saminium.com")
                                .name("Developer: Sam"))
                        .license(new License().name("Saminium LLC"))
                        .version("2.0"));
    }
}