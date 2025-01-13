package com.example.vinhomeproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableWebSecurity
@OpenAPIDefinition(
        info = @Info(
                title = "VINHOME_Apartment_Rent",
                version = "1.0.0",
                description = "SWD392 Project Spring24",
                termsOfService = "Whale house",
                contact = @Contact(
                        name = "Mr.Thắng",
                        email = "dmthang.longan@gmail.com"
                ),
                license = @License(
                        name = "licence",
                        url = "whalehouse"
                )
        ),
        servers = {
                @Server(url = "https://whalehome.up.railway.app/", description = "Default Server URL"),
                @Server(url = "http://localhost:8080/", description = "Default Server URL")
        }
)
public class VinHomeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinHomeProjectApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
