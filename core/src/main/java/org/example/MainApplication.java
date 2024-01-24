package org.example;

import com.example.models.board.Board;
import org.example.repositoryes.interfaces.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example", "com.example.models.pieces"})
public class MainApplication {

    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }

}