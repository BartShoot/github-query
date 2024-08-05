package com.bartshoot.github_query.config;

import com.bartshoot.github_query.client.GitHubClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfiguration {

    @Bean
    GitHubClient gitHubClient() {
        RestClient restClient = RestClient.builder().baseUrl("https://api.github.com/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(GitHubClient.class);
    }
}