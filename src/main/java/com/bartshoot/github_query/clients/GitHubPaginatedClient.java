package com.bartshoot.github_query.clients;

import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface GitHubPaginatedClient {

    @GetExchange("users/{userName}/repos")
    ResponseEntity<List<Repository>> getUserRepositories(@PathVariable String userName,
                                                         @RequestParam(value = "page") int page);

    @GetExchange("repos/{userName}/{repoName}/branches")
    ResponseEntity<List<Branch>> getRepoBranches(@PathVariable String userName, @PathVariable String repoName,
                                                 @RequestParam(value = "page") int page);
}