package com.serasa.registerandscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RegisterAndScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterAndScoreApplication.class, args);
    }

}
