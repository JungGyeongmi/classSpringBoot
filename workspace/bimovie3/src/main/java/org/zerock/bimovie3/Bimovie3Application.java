package org.zerock.bimovie3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Bimovie3Application {

	public static void main(String[] args) {
		SpringApplication.run(Bimovie3Application.class, args);
	}

}
