package com.bartshoot.github_query.models;

import java.util.List;

public class RepositoryFront {
    private final String repositoryName;
    private final String ownerLogin;
    private final List<BranchFront> branches;

    public RepositoryFront(Repository repository, List<Branch> branches) {
        this.repositoryName = repository.name();
        this.ownerLogin = repository.owner().login();
        this.branches = branches.stream().map(BranchFront::new).toList();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchFront> getBranches() {
        return branches;
    }

}
