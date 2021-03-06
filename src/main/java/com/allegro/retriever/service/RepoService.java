package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepoService {

    private final GithubClient githubClient;

    @Autowired
    RepoService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public Repos getRepos(String name) {

        return githubClient.getRepos(name);
    }

    public int countStars(String name) {
        Repos repos = githubClient.getRepos(name);
        return repos.countStars();
    }
}
