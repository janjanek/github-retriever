package com.allegro.retriever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GithubDataRetrieverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubDataRetrieverApplication.class, args);

    }

}
