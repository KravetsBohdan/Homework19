package com.bkravets.homework19;

import com.bkravets.homework19.service.PhotoService;
import com.bkravets.homework19.service.StudentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
@OpenAPIDefinition(
        servers = @Server(url = "/")
)
public class Homework19Application {
    private final StudentService studentService;
    private final PhotoService photoService;

    public static void main(String[] args) {
        SpringApplication.run(Homework19Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            photoService.getPhotosByDescription("selfie").forEach(System.out::println);
        };
    }

}
