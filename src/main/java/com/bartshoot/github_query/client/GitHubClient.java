package com.bartshoot.github_query.client;

import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface GitHubClient {
    @GetExchange("users/{userName}/repos")
    List<Repository> getUserRepositories(@PathVariable String userName);

    @GetExchange("repos/{userName}/{repoName}/branches")
    List<Branch> getRepoBranches(@PathVariable String userName, @PathVariable String repoName);
}