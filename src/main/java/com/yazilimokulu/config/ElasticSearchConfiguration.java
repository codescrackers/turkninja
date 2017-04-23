package com.yazilimokulu.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by edsoft on 08.04.2017.
 */
@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
@EnableElasticsearchRepositories(basePackages = {
        "com.yazilimokulu.mvc.daos.elastic"})
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.clustername}")
    private String clusterName;

    @Bean
    public Client client() throws UnknownHostException {
        Settings setting = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .build();
        return TransportClient.builder()
                .settings(setting)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
    }

    @Bean
    public ElasticsearchOperations elasticSearchTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(client());
    }
}
