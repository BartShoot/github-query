package com.bartshoot.github_query.services;

import com.bartshoot.github_query.client.GitHubClient;
import org.springframework.stereotype.Service;

@Service
public class UserRepoServiceImpl implements UserRepoService {

    private final GitHubClient gitHubClient;

    public UserRepoServiceImpl(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public Object getRepos(String userName) {

        return gitHubClient.getUserRepositories(userName);
    }
}
