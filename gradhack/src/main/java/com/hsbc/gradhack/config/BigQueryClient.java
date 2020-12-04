package com.hsbc.gradhack.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class BigQueryClient {

    @Value("${config.projectId}")
    private String projectId;
    @Value("${config.credential}")
    private String credential;

    @Bean(name = "BQClient")
    BigQuery createBQClient() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/" + credential);
        InputStream inputStream = classPathResource.getInputStream();
        BigQuery bigquery = BigQueryOptions.newBuilder().setProjectId(projectId)
                .setCredentials(ServiceAccountCredentials.fromStream(inputStream))
                .build().getService();
        return  bigquery;
    }

}
