package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import com.allegro.retriever.domain.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Service
public class GithubClient implements RepoClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Repos getRepos(String url) {
        Repo[] response = restTemplate.getForObject(url, Repo[].class);
        Repos repos = new Repos();
        repos.setRepos(Arrays.asList(response));
        return repos;
    }

}