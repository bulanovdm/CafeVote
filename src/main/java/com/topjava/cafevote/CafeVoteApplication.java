package com.topjava.cafevote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CafeVoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(CafeVoteApplication.class, args);
    }
}
