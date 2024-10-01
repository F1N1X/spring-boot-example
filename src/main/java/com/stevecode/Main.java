package com.stevecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public GreedResponse greet(@RequestParam(value = "name", required = false) String name) {
        //http://localhost:8080/greet?name=Peter
        String greetMessage = name == null || name.isBlank() ? "Hello" : "Hello " + name;
        GreedResponse response = new GreedResponse(
                greetMessage,
                List.of("Python","Java","JavaScript"),
                new Person("Martin", 30, 10_000)
                );

        return response;
    }

    record Person(String name, int age, double savings){}

    record GreedResponse(
            String greet,
            List<String> favoriteProgrammingLanguages,
            Person person
            ) {}

}
