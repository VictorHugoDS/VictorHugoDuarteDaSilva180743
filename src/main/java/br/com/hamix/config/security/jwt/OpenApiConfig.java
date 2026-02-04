package br.com.hamix.config.security.jwt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(title = "Hamix API", version = "v1"),
		security = @SecurityRequirement(name = "bearerAuth"),
		tags = {
				@Tag(name = "Artista", description = "Endpoints de cadastro e consulta de artistas"),
				@Tag(name = "Album", description = "Endpoints de cadastro e consulta de albuns"),
				@Tag(name = "Regionais", description = "Endpoints de sincronização de regionais"),
				@Tag(name = "Autenticação", description = "Endpoints de autenticação e renovação de tokens")
		}
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
