package com.allegro.retriever.service;

import com.allegro.retriever.domain.Repo;
import com.allegro.retriever.domain.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class RestGithubClient implements GithubClient {

    @Value("${github_url}")
    private String githubUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public Repos getRepos(String name) {

        String url = githubUrl + "/users/" + name + "/repos?page=1&per_page=100";
        Repo[] response = restTemplate.getForObject(url, Repo[].class);
        Repos repos = new Repos(Arrays.asList(response));
        return repos;

    }
}


//    @Override
//    public Repos getRepos(String name) {
//
//        //   https://api.github.com/users/allegro/repos?page=1&per_page=100
//        int counter = 1;
//        Repos reposResult = new Repos();
//        do {
//            String url = "https://api.github.com/users/" + name + "/repos?page=" + counter + "&per_page=100";
//            Repo[] response = restTemplate.getForObject(url, Repo[].class);
//            Repos repos = new Repos();
//            repos.setRepos(Arrays.asList(response));
//            if(repos.getRepos().isEmpty()){
//                break;
//            }
//            reposResult.setRepos(Stream.concat(reposResult.getRepos().stream(), repos.getRepos().stream())
//                    .collect(Collectors.toList()));
//            counter++;
//        }while(counter != 6);
//        return reposResult;
//
//    }