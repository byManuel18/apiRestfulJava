package com.manuel.ApiResfulSpoify.confi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
			.basePackage("com.manuel.ApiResfulSpoify")).paths(PathSelectors.any()).build().tags(new Tag("GÉNERO", "Controlador del GÉNERO"),
					new Tag("ARTISTA","Controlador de los ARTISTAS"),new Tag("USUARIO","Controlador de los USUARIOS"),new Tag("CANCIONES","Controlador de las CANCIONES"),
					new Tag("DISCOS","Controlador de los DISCOS"),new Tag("LISTAS REPRODUCCION","Controlador de las LISTAS DE REPRODUCCIÓN"));
	}
}
