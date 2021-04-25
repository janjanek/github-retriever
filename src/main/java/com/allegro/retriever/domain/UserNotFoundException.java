package com.allegro.retriever.domain;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String user) {
        super("User: " + user + " not found.");
    }
}
