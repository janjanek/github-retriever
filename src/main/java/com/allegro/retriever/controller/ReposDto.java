package com.allegro.retriever.controller;

import lombok.Value;

import java.util.List;

@Value
public class ReposDto {
    List<RepoDto> repos;
}
