package com.bartshoot.github_query.controllers;

import com.bartshoot.github_query.models.RepositoryFront;
import com.bartshoot.github_query.validators.ValidGitHubUserName;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public interface GithubUserQueryController {

    @GetMapping("user/{userName}/repos")
    @Validated
    List<RepositoryFront> getUserRepos(@PathVariable @Valid @ValidGitHubUserName String userName);
}
