package com.bartshoot.github_query.models;

public record Branch(String name, Commit commit) {
    public record Commit(String sha) {
    }
}
