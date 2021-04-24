package com.allegro.retriever.controller;

import com.allegro.retriever.domain.Repos;
import com.allegro.retriever.service.JsonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    private static final String DATA_URL = "https://api.github.com/users/allegro/repos?per_page=100";

    @Autowired
    private JsonClientService clientService;

    @GetMapping("/users/{name}/repos")
    public @ResponseBody Repos MainApi(@PathVariable(value = "name") String name) {                //may add later throws NoSuchFieldException

        String url = "https://api.github.com/users/" + name + "/repos?per_page=100";

        return clientService.parseList(url);
    }
}
