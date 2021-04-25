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
    private RepoService repoService;

    @GetMapping("/users/{name}/repos")
    public @ResponseBody
    ReposDto getRepos(@PathVariable(value = "name") String name) {                //may add later throws NoSuchFieldException

        ReposDto reposDto = repoService.getRepos(name).toDto();
        log.info("Retrieving repos: {} for user: {}", reposDto, name);
        return reposDto;
    }

    @GetMapping("/users/{name}/stars")
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
