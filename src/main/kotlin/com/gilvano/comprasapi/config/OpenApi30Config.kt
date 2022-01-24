package com.gilvano.comprasapi.config





import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApi30Config{

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Compras API")
                .version("1.0.0")
                .description("API para acesso ao sistema de compras"))
            .components(Components().addSecuritySchemes(
                "bearerAuth",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .name("bearerAuth")
            ))
    }

}