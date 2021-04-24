package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repos;


public interface GithubClient {
    Repos getRepos(String name);
}