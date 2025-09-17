package br.com.ifpe.oxefood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing

@SpringBootApplication
public class OxefoodApiViniciusApplication {

	public static void main(String[] args) {
		SpringApplication.run(OxefoodApiViniciusApplication.class, args);
	}

}
