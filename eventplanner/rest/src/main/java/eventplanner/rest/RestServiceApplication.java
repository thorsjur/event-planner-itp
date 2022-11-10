package eventplanner.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import eventplanner.json.CustomObjectMapper;

/**
 * Application for starting the Spring Boot REST server.
 */
@SpringBootApplication
public class RestServiceApplication {

    /**
     * Gets mapper used to map events and users to JSON, and vice versa.
     * 
     * @return the custom object mapper to be used for serialization and
     *         deserialization.
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}