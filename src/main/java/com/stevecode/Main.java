package com.stevecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication

public class Main {






    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(Main.class, args);

    }


    //Foo is outside of the Application-Context -> Not management for us
    //Bean -> in Application Context
    @Bean("foo")
    public Foo getFoo() {
        //Do mutch logic before instance from spring
        return new Foo("SuperFoo");
    }

    record Foo(String name){}



}
