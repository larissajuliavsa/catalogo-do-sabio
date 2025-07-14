package com.catalogodosabio.catalogo.config;

import com.catalogodosabio.catalogo.model.Book;
import com.catalogodosabio.catalogo.repository.BookRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.stream.IntStream;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(BookRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));
                IntStream.range(0, 50).forEach(i -> {
                    Book book = Book.builder()
                            .title(faker.book().title())
                            .author(faker.book().author())
                            .genre(faker.book().genre())
                            .description(faker.lorem().paragraph(3))
                            .build();
                    repository.save(book);
                });
                System.out.println("Livros gerados com sucesso!");
            }
        };
    }
}
