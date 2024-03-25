package com.nuclea.reembolsoapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Reembolso API", version = "2.0", description = "Api para reembolso"))
public class ReembolsoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReembolsoApiApplication.class, args);
	}

}
