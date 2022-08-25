package com.cisco.sibs.swaggerConfig;

import java.lang.annotation.Annotation;

import org.elasticsearch.rest.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket getDocket()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("public-apis")
				.apiInfo(getApiInfo())
	            .select()
	            .apis(RequestHandlerSelectors.withClassAnnotation((Class<? extends Annotation>) RestController.class))
	            .build();
	            
				
	}

	private ApiInfo getApiInfo() {
		// TODO Auto-generated method stub
		return new ApiInfoBuilder().title("SAIB APIS").build();
	}
}
