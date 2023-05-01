package com.example.demoWeb;

import com.sun.tools.javac.Main;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Операции с вагонами", version = "2.0", description = "Virst Information"))
/*@SecurityScheme(name = "user", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)*/

public class DemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}

}
