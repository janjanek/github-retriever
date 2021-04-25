package com.allegro.retriever.controller;

import com.allegro.retriever.domain.UserNotFoundException;
import com.allegro.retriever.service.RepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    public ApiController(RepoService repoService) {
        this.repoService = repoService;
    }

    private final RepoService repoService;

    @GetMapping(path = "/users/{name}/repos", produces = "application/json")
    public @ResponseBody
    ReposDto getRepos(@PathVariable(value = "name") String name) {

        ReposDto reposDto = repoService.getRepos(name).toDto();
        log.info("Retrieving repos: {} for user: {}", reposDto, name);
        return reposDto;
    }

    @GetMapping(path = "/users/{name}/stars", produces = "application/json")
    public @ResponseBody
    StarsDto getStarsCount(@PathVariable(value = "name") String name) {
        StarsDto starsDto = new StarsDto(repoService.countStars(name));
        log.info("Retrieving stars count: {} for user: {}", starsDto, name);
        return starsDto;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void on404() {
    }
}
