package com.bartshoot.github_query.configs;

import com.bartshoot.github_query.clients.GitHubClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@PropertySource("classpath:application.properties")
public class ClientConfiguration {

    @Value("${github.api-key}")
    private String gitHubApiKey;

    @Bean
    GitHubClient gitHubClient() {
        RestClient.Builder restClient = RestClient.builder().baseUrl("https://api.github.com/");
        if (!gitHubApiKey.isBlank()) {
            restClient.defaultHeader("Authorization", "Bearer " + gitHubApiKey);
        }
        RestClientAdapter adapter = RestClientAdapter.create(restClient.build());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubClient.class);
    }
}