package com.bartshoot.github_query.clients;

import com.bartshoot.github_query.models.Branch;
import com.bartshoot.github_query.models.Repository;
import com.bartshoot.github_query.services.GitHubApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

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
        ResponseEntity<List<T>> currentResponse;
        int page = 1;
        currentResponse = apiCall.apply(page);
        List<T> results = new ArrayList<>(Objects.requireNonNull(currentResponse.getBody()));
        if (hasNextPage(currentResponse)) {
            IntStream.range(2, getPageCount(currentResponse)).parallel().forEach(
                    p -> results.addAll(Objects.requireNonNull(apiCall.apply(p).getBody())));
        }
        return results;
    }

    private <T> int getPageCount(ResponseEntity<List<T>> currentResponse) {
        String link = Arrays.stream(currentResponse.getHeaders().get("link").getFirst().split(",")).filter(
                it -> it.contains("last")).findFirst().get();
        return Integer.parseInt(link.substring(link.lastIndexOf("page=") + 5, link.indexOf(">;")));
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
