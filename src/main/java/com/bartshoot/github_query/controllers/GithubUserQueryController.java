package com.bartshoot.github_query.controllers;

import com.bartshoot.github_query.models.RepositoryFront;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
public interface GithubUserQueryController {

    @GetMapping("user/{userName}/repos")
    @Validated
    List<RepositoryFront> getUserRepos(@PathVariable String userName);
}
