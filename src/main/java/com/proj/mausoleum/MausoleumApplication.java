package com.proj.mausoleum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MausoleumApplication {

    public static void main(String[] args) {
        SpringApplication.run(MausoleumApplication.class, args);
        System.out.println(System.getProperty("user.dir"));
    }
}
