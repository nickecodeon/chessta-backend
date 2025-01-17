package org.example.chessta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.chessta.model.game")
public class ChesstaApp {

    public static void main(String[] args) {
        SpringApplication.run(ChesstaApp.class, args);
    }
}
