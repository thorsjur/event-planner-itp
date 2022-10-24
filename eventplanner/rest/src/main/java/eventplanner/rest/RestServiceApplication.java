package eventplanner.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import eventplanner.json.CustomObjectMapper;

@SpringBootApplication
public class RestServiceApplication {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}