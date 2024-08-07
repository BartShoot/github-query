package com.bartshoot.github_query.configs;

import com.bartshoot.github_query.clients.GitHubPaginatedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:authentication.properties")
public class ClientConfiguration {

    @Value("${github.api-key:}")
    private String gitHubApiKey;

    @Bean
    GitHubPaginatedClient gitHubClient() {
        RestClient.Builder restClient = RestClient.builder()
                .baseUrl("https://api.github.com/")
                .requestInterceptor(new LoggingInterceptor());
        if (!gitHubApiKey.isBlank()) {
            restClient.defaultHeader("Authorization", "Bearer " + gitHubApiKey);
        }
        RestClientAdapter adapter = RestClientAdapter.create(restClient.build());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubPaginatedClient.class);
    }
}