package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import com.allegro.retriever.domain.Repos;
import com.allegro.retriever.domain.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Slf4j
@Service
public class RestGithubClient implements GithubClient {

    @Value("${github_url}")
    private String githubUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public RestGithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    @Cacheable(value = "repos")
    public Repos getRepos(String name) {
        String url = githubUrl + "/users/" + name + "/repos?page=1&per_page=100";
        log.info("Performing get request: " + url);
        try {
            Repo[] response = restTemplate.getForObject(url, Repo[].class);
            return new Repos(Arrays.asList(response));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("User: " + name + " not found.");
                throw new UserNotFoundException(name);
            }
            throw e;
        }
    }
}
