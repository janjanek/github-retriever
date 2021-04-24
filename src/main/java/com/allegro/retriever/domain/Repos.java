package com.allegro.retriever.domain;

import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repos {

    private List<Repo> repos;

    public Repos() {
        repos = new ArrayList<>();
    }

    @JsonGetter
    public List<Repo> getRepos() {
        return repos;
    }

    @JsonSetter
    public void setRepos(List<Repo> repos) {
        this.repos = repos;
    }

    public int countStars(){
        return repos.stream().mapToInt(Repo::getStars).sum();
    }
}
