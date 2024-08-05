package com.bartshoot.github_query.models;

public record Repository(Owner owner, String name, Boolean fork) {
    public record Owner(String login) {
    }
}
