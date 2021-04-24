package com.allegro.retriever.domain;

import com.allegro.retriever.controller.RepoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Repo {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "stargazers_count")
    private int stars;


    public RepoDto toDto() {
        return new RepoDto(name, stars);
    }
}
