package com.bartshoot.github_query.services;

import com.bartshoot.github_query.client.GitHubClient;
import com.bartshoot.github_query.models.Repository;
import com.bartshoot.github_query.models.RepositoryFront;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepoServiceImpl implements UserRepoService {

    private final GitHubClient gitHubClient;

    public UserRepoServiceImpl(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public List<RepositoryFront> getRepos(String userName) {
        List<Repository> userRepositories = gitHubClient.getUserRepositories(userName);
        return userRepositories.stream().filter(repository -> !repository.fork()).map(
                repository -> new RepositoryFront(repository,
                        gitHubClient.getRepoBranches(userName, repository.name()))).toList();
    }
}
