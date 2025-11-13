package com.parcialspring.parcialspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class ParcialspringApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ParcialspringApplication.class, args);
	}

}
