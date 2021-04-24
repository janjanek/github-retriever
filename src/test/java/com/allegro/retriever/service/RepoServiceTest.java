package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import com.allegro.retriever.domain.Repos;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RepoServiceTest {


    GithubClient githubClient;

    RepoService repoService;

    @BeforeEach
    public void init() {
        githubClient = Mockito.mock(GithubClient.class);
        repoService = new RepoService(githubClient);
    }

    @Test
    void should_return_empty_repos() {
        //given
        Mockito.when(githubClient.getRepos("name")).thenReturn(emptyRepos());

        //when
        Repos result = repoService.getRepos("name");

        //then
        assertEquals(emptyRepos(), result);
    }

    @Test
    void should_return_repos() {
        //given
        Mockito.when(githubClient.getRepos("name")).thenReturn(someRepos());

        //when
        Repos result = repoService.getRepos("name");

        //then
        assertEquals(someRepos(), result);
    }

    @Test
    void should_count_stars_from_empty_repos() {
        //given
        Mockito.when(githubClient.getRepos("name")).thenReturn(emptyRepos());

        //when
        int repos = repoService.countStars("name");

        //then
        assertEquals(0, repos);
    }

    @Test
    void should_count_stars_from_repos() {
        //given
        Mockito.when(githubClient.getRepos("name")).thenReturn(someRepos());

        //when
        int repos = repoService.countStars("name");

        //then
        assertEquals(12, repos);
    }

    static private Repos emptyRepos() {
        return new Repos(Lists.emptyList());
    }

    static private Repos someRepos() {
        Repo someRepo = new Repo("allegro", 8);
        Repo anotherRepo = new Repo("ebay", 4);
        return new Repos(Arrays.asList(someRepo, anotherRepo));
    }
}