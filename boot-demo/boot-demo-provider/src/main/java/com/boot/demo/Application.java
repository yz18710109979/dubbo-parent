package com.boot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
//    public static void main(String[] args) throws IOException {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
//        context.start();
//        System.in.read();
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
