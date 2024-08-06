package com.bartshoot.github_query.clients;

public record GitHubErrorResponse(int status, String message, String documentation_url) {
}
