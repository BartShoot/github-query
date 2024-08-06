package com.bartshoot.github_query.services;

import com.bartshoot.github_query.models.RepositoryFront;

import java.util.List;

public interface UserRepoService {
    List<RepositoryFront> getRepos(String userName);
}
