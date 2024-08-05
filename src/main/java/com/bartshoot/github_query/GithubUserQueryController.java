package com.bartshoot.github_query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubUserQueryController {
    @GetMapping("user/{userName}/repos")
    public Object getUserRepos(@PathVariable("userName") String userName) {
        return userName;
    }
}
