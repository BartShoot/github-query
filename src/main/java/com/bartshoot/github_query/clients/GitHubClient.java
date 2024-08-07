package com.bartshoot.github_query.clients;

import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;

import java.util.List;

public interface GitHubClient {

    List<Branch> getRepoBranches(String userName, Repository repository);

    List<Repository> getUserRepositories(String userName);
}
