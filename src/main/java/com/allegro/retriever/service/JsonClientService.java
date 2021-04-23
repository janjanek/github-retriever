package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonClientService implements ClientService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object parse(String url) {
        return restTemplate.getForObject(url, Object.class);
    }

    @Override
    public List<Repo> parseList(String url) {
        Repo[] response = restTemplate.getForObject(url, Repo[].class);
        List<Repo> repos = Arrays.asList(response);
        return repos;
    }

    public void postList(List<Repo>repos, String url) {
        restTemplate.postForObject(url, repos, ResponseEntity.class);
    }
}