package br.com.foodtosave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class FoodtosaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodtosaveApplication.class, args);
	}

}
