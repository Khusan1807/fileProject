package com.example.file;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@SpringBootApplication
public class FileApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }


}
