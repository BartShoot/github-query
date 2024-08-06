package com.bartshoot.github_query.controllers;

import com.bartshoot.github_query.clients.GitHubApiException;
import com.bartshoot.github_query.models.RepositoryFront;
import com.bartshoot.github_query.services.UserRepoService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubUserQueryControllerImpl implements GithubUserQueryController {

    private final UserRepoService userRepoService;

    public GithubUserQueryControllerImpl(UserRepoService userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public List<RepositoryFront> getUserRepos(String userName) {
        return userRepoService.getRepos(userName);
    }

    @ExceptionHandler
    public ApiError handle(GitHubApiException e) {
        return new ApiError(e.getErrorResponse().status(), e.getErrorResponse().message());
    }
}
