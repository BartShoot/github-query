package com.bartshoot.github_query.models;

public class BranchFront {
    private final String name;
    private final String lastCommitSha;

    public BranchFront(Branch branch) {
        this.name = branch.name();
        this.lastCommitSha = branch.commit().sha();
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

    public String getName() {
        return name;
    }
}
