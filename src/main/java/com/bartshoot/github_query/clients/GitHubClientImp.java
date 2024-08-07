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
        return getPaginatedResults(page -> gitHubPaginatedClient.getRepoBranches(userName, repository.name(), page));
    }

    @Override
    public List<Repository> getUserRepositories(String userName) {
        return getPaginatedResults(page -> gitHubPaginatedClient.getUserRepositories(userName, page));
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

    private static <T> boolean hasNextPage(ResponseEntity<T> currentResponse) {
        if (currentResponse.getHeaders().get("link") == null) {
            return false;
        }
        return currentResponse.getHeaders().get("link").stream().anyMatch(s -> s.contains("; rel=\"next\""));
    }

    private GitHubErrorResponse parseErrorResponse(HttpClientErrorException e) {
        GitHubErrorResponse gitHubErrorResponse = e.getResponseBodyAs(GitHubErrorResponse.class);
        if (gitHubErrorResponse != null && gitHubErrorResponse.status() == 0) {
            gitHubErrorResponse = new GitHubErrorResponse(e.getStatusCode().value(), gitHubErrorResponse.message(),
                    gitHubErrorResponse.documentation_url());
        }
        return gitHubErrorResponse;
    }
}
