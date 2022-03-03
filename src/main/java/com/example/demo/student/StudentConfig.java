package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student nikola = new Student("Nikola", "nikola@gmail.com", LocalDate.of(1998, Month.JULY, 29));
            Student alex = new Student("Alex", "alex@outlook.com", LocalDate.of(2000, Month.AUGUST, 18));
            Student mariam = new Student("Mariam", "mariam@gmail.com", LocalDate.of(1992, Month.JULY, 21));

            repository.saveAll(List.of(nikola, alex, mariam));
        };
    }
}
