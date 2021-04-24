package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import com.allegro.retriever.domain.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class JsonClientService implements ClientService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Repos parseList(String url) {
        Repo[] response = restTemplate.getForObject(url, Repo[].class);
        Repos repos = new Repos();
        repos.setRepos(Arrays.asList(response));
        return repos;
    }

    @Override
    public int countStars(String url) {
        Repo[] response = restTemplate.getForObject(url, Repo[].class);
        Repos repos = new Repos();
        repos.setRepos(Arrays.asList(response));
        return repos.countStars();
    }

    public void postList(Repos repos, String url) {
        restTemplate.postForObject(url, repos, ResponseEntity.class);
    }
}