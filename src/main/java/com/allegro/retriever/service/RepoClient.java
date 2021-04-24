package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repos;


public interface RepoClient {

    /**
     * Service to parse json data to the
     * class object
     */

    Repos getRepos(String url);

    void postList(Repos repos, String url);
}