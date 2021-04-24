package com.allegro.retriever.domain;

import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repos {

    private final List<Repo> repos;

    public Repos(List<Repo> repos) {
        this.repos = repos;
    }

    @JsonGetter
    public List<Repo> getRepos() {
        return repos;
    }

    public int countStars() {
        return repos.stream().mapToInt(Repo::getStars).sum();
    }
}
