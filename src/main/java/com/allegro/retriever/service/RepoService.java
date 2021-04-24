package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepoService {

    @Autowired
    private GithubClient githubClient;

    public Repos getRepos(String url) {
        return githubClient.getRepos(url);
    }

    public int countStars(String url) {
        Repos repos = githubClient.getRepos(url);
        return repos.countStars();
    }
}
