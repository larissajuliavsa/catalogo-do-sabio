package com.catalogodosabio.catalogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

}
