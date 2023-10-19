package org.filmland;

import org.filmland.category.CategoryRepository;
import org.filmland.entities.Category;
import org.filmland.users.UserRepository;
import org.filmland.entities.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.stream.Stream;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner users(UserRepository userRepository) {
        return args -> {
            Stream.of("test1@test.nl", "info@filmland-assessment.nl").forEach(email -> {
                Users user = new Users();
                user.setEmail(email);
                user.setPassword("Javaiscool90");
                userRepository.save(user);
            });
        };
    }

    @Bean
    CommandLineRunner categories(CategoryRepository categoryRepositoryRepository) {
        return args -> {
            Stream.of("Dutch Series", "Dutch Films", "International Films").forEach(newCategory -> {
                Category category = new Category();
                category.setName(newCategory);
                category.setPrice(10);
                category.setAvailableContent(20);
                categoryRepositoryRepository.save(category);
            });
        };
    }
}