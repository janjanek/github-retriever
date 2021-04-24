package com.allegro.retriever.domain;

import com.allegro.retriever.controller.ReposDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repos {

    List<Repo> repos;

    public Repos(List<Repo> repos) {
        this.repos = repos;
    }

    public int countStars() {
        return repos.stream().mapToInt(Repo::getStars).sum();
    }

    public ReposDto toDto() {
        return new ReposDto(repos.stream()
                .map(Repo::toDto)
                .collect(Collectors.toList())
        );
    }
}
