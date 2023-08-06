package br.com.reinan.dscatalog.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer ",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPi() {
        return new OpenAPI()
                .info(new Info()
                        .title("DsCatalog API")
                        .version("v1")
                        .description("A simple API named DsCatalog, which stands for \"Digital Store Catalog,\" provides basic CRUD (Create, Read, Update, Delete) functionality for managing products, categories, and users. The API includes various endpoints to perform operations on these entities.")
                        .termsOfService("https://www.reinan.com.br/termsOfServices")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.reinan.com.br")));

    }

}
