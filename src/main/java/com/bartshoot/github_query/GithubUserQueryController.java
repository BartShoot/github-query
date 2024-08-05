package com.bartshoot.github_query;

import com.bartshoot.github_query.services.UserRepoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubUserQueryController {
    private final UserRepoService userRepoService;

    public GithubUserQueryController(UserRepoService userRepoService) {
        this.userRepoService = userRepoService;
    }

    @GetMapping("user/{userName}/repos")
    public Object getUserRepos(@PathVariable("userName") String userName) {
        return userRepoService.getRepos(userName);
    }
}
