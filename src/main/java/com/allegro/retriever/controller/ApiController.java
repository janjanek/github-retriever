package com.allegro.retriever.controller;

import com.allegro.retriever.domain.Repos;
import com.allegro.retriever.service.JsonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final String DATA_URL = "https://api.github.com/users/allegro/repos?per_page=100";

    @Autowired
    private JsonClientService clientService;

    @GetMapping("/user/repos")
    public Repos MainApi() {                                //may add later throws NoSuchFieldException

        return clientService.parseList(DATA_URL);
    }
}
