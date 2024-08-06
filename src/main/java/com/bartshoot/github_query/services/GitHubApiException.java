package com.bartshoot.github_query.services;

import com.bartshoot.github_query.clients.GitHubErrorResponse;

public class GitHubApiException extends RuntimeException {
    private final GitHubErrorResponse errorResponse;

    public GitHubApiException(GitHubErrorResponse errorResponse) {
        super(errorResponse.message());
        this.errorResponse = errorResponse;
    }

    public GitHubErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
