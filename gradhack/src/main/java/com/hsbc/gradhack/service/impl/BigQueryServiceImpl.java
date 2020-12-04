package com.hsbc.gradhack.service.impl;

import com.google.cloud.bigquery.*;
import com.hsbc.gradhack.domain.User;
import com.hsbc.gradhack.service.BigQueryService;
import com.hsbc.gradhack.service.CloudSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BigQueryServiceImpl implements BigQueryService {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CloudSQLService cloudSQLService;

    @Override
    public Double generateCredit(String name) throws InterruptedException {
        BigQuery bigQuery = (BigQuery) applicationContext.getBean("BQClient");
        User user = cloudSQLService.getUser(name);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT predicted_credit FROM ML.PREDICT(MODEL `gradhack.credit_model`,(select ")
                .append(user.getTransaction()).append(" as transaction, ")
                .append(user.getIncome()).append(" as income, ")
                .append(user.getOverdue()).append(" as overdue, ")
                .append(user.getLevel()).append(" as level))");
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(sb.toString())
                .setUseLegacySql(false).build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        TableResult result = queryJob.getQueryResults();

        Double value = new Double(0);

        for (FieldValueList row : result.iterateAll()) {
            String predicted_credit = row.get("predicted_credit").getStringValue();
            value = Double.valueOf(String.format("%.2f", Double.valueOf(predicted_credit).doubleValue()));
        }

        cloudSQLService.updateCredit(value, name);

        return value;
    }
}
