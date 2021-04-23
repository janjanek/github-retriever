package com.allegro.retriever.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final String DATA_URL = "https://api.github.com/users/allegro/repos?per_page=100";


    @GetMapping("/")
    public String MainApi() {
        return "Hello main!";
    }
}
