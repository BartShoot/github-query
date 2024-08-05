package com.bartshoot.github_query.models;

public class BranchFront {
    private final String name;
    private final String sha;

    public BranchFront(Branch branch) {
        this.name = branch.name();
        this.sha = branch.commit().sha();
    }

    public String getSha() {
        return sha;
    }

    public String getName() {
        return name;
    }
}
