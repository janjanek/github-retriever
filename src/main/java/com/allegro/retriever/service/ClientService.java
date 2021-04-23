package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;

import java.util.List;

public interface ClientService {

    /**
     * Service to parse json data to the
     * class object
     */
    Object parse(String url);

    List<Repo> parseList(String url);
}