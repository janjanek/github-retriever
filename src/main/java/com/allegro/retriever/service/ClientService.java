package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repos;


public interface ClientService {

    /**
     * Service to parse json data to the
     * class object
     */

    Repos parseList(String url);

    void postList(Repos repos, String url);
}