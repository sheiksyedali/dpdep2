package com.ecomshop.deskplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DeskPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeskPlusApplication.class, args);
    }

}
