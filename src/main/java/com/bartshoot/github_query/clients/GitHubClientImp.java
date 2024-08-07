package com.bartshoot.github_query.clients;

import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;
import com.bartshoot.github_query.services.GitHubApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class GitHubClientImp implements GitHubClient {

    private final GitHubPaginatedClient gitHubPaginatedClient;

    public GitHubClientImp(GitHubPaginatedClient gitHubPaginatedClient) {
        this.gitHubPaginatedClient = gitHubPaginatedClient;
    }

    @Override
    public List<Branch> getRepoBranches(String userName, Repository repository) {
        try {
            return getPaginatedResults(
                    page -> gitHubPaginatedClient.getRepoBranches(userName, repository.name(), page));
        } catch (HttpClientErrorException e) {
            throw new GitHubApiException(
                    parseErrorResponse(e, "Repository " + userName + "/" + repository.name() + " branches"));
        }
    }

    @Override
    public List<Repository> getUserRepositories(String userName) {
        try {
            return getPaginatedResults(page -> gitHubPaginatedClient.getUserRepositories(userName, page));
        } catch (HttpClientErrorException e) {
            throw new GitHubApiException(parseErrorResponse(e, "User " + userName));
        }
    }

    private <T> List<T> getPaginatedResults(Function<Integer, ResponseEntity<List<T>>> apiCall) {
        List<T> results = new ArrayList<>();
        ResponseEntity<List<T>> currentResponse;
        boolean hasMorePages;
        int page = 1;
        do {
            currentResponse = apiCall.apply(page);
            results.addAll(Objects.requireNonNull(currentResponse.getBody()));
            hasMorePages = hasNextPage(currentResponse);
            page++;
        } while (hasMorePages);
        return results;
    }

    private static <T> boolean hasNextPage(ResponseEntity<T> currentResponse) {
        if (currentResponse.getHeaders().get("link") == null) {
            return false;
        }
        return currentResponse.getHeaders().get("link").stream().anyMatch(s -> s.contains("; rel=\"next\""));
    }

    private GitHubErrorResponse parseErrorResponse(HttpClientErrorException e, String s) {
        GitHubErrorResponse gitHubErrorResponse = e.getResponseBodyAs(GitHubErrorResponse.class);
        int status = gitHubErrorResponse != null ? gitHubErrorResponse.status() : 0;
        return new GitHubErrorResponse(
                status == 0 ? e.getStatusCode().value() : status,
                s + " " + (gitHubErrorResponse != null ? gitHubErrorResponse.message() : ""),
                gitHubErrorResponse != null ? gitHubErrorResponse.documentation_url() : null);
    }
}
