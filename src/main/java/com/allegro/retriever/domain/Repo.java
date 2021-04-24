package com.allegro.retriever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

    private String name;
    private int stars;

    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    @JsonProperty(value = "name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "stargazersCount")
    public int getStars() {
        return stars;
    }

    @JsonProperty(value = "stargazers_count")
    public void setStars(int stars) {
        this.stars = stars;
    }


}
