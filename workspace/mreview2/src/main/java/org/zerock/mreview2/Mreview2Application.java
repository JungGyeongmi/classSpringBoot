package org.zerock.mreview2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Mreview2Application {

	public static void main(String[] args) {
		SpringApplication.run(Mreview2Application.class, args);
	}

}
