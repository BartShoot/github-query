package com.bartshoot.github_query.services;

import com.bartshoot.github_query.clients.GitHubApiException;
import com.bartshoot.github_query.clients.GitHubClient;
import com.bartshoot.github_query.clients.GitHubErrorResponse;
import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;
import com.bartshoot.github_query.models.RepositoryFront;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class UserRepoServiceImpl implements UserRepoService {

    private final GitHubClient gitHubClient;

    private final ObjectMapper objectMapper;

    public UserRepoServiceImpl(GitHubClient gitHubClient, ObjectMapper objectMapper) {
        this.gitHubClient = gitHubClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<RepositoryFront> getRepos(String userName) {
        List<Repository> userRepositories = getUserRepositories(userName);
        return userRepositories.stream()
                .filter(repository -> !repository.fork())
                .map(repository -> new RepositoryFront(repository, getRepoBranches(userName, repository)))
                .toList();
    }

    private List<Branch> getRepoBranches(String userName, Repository repository) {
        return getPaginatedResults(page -> gitHubClient.getRepoBranches(userName, repository.name(), page));
    }

    private List<Repository> getUserRepositories(String userName) {
        return getPaginatedResults(page -> gitHubClient.getUserRepositories(userName, page));
    }

    private <T> List<T> getPaginatedResults(Function<Integer, ResponseEntity<List<T>>> apiCall) {
        List<T> results = new ArrayList<>();
        ResponseEntity<List<T>> currentResponse;
        boolean hasMorePages;
        int page = 1;
        do {
            try {
                currentResponse = apiCall.apply(page);
            } catch (HttpClientErrorException e) {
                throw new GitHubApiException(parseErrorResponse(e));
            }
            results.addAll(Objects.requireNonNull(currentResponse.getBody()));
            hasMorePages = hasNextPage(currentResponse);
            page++;
        } while (hasMorePages);
        return results;
    }

    private GitHubErrorResponse parseErrorResponse(HttpClientErrorException e) {
        String json = e.getMessage().substring(e.getMessage().indexOf('{'));
        try {
            GitHubErrorResponse gitHubErrorResponse = objectMapper.readValue(json, GitHubErrorResponse.class);
            if (gitHubErrorResponse.status() == 0) {
                gitHubErrorResponse = new GitHubErrorResponse( e.getStatusCode().value(), gitHubErrorResponse.message(),
                        gitHubErrorResponse.documentation_url());
            }
            return gitHubErrorResponse;
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static <T> boolean hasNextPage(ResponseEntity<T> currentResponse) {
        if (currentResponse.getHeaders().get("link") == null) {
            return false;
        }
        return currentResponse.getHeaders().get("link").stream().anyMatch(s -> s.contains("; rel=\"next\""));
    }
}
