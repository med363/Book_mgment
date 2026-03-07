package com.example.bookMgment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BookMgmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookMgmentApplication.class, args);
	}

	@GetMapping("/test")
	public String test() {
		return "App is running!";
	}
}