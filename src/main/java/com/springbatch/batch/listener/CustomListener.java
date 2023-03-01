package com.springbatch.batch.listener;

import com.springbatch.entity.Advertisement;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomListener {
    private JobExecution jobExecution;
    private ExecutionContext executionContext;

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        this.jobExecution = jobExecution;
        this.executionContext = jobExecution.getExecutionContext();
        executionContext.putLong("impressionsSum", 0);
        executionContext.putLong("itemsCount", 0);
    }

    @BeforeWrite
    public void beforeWrite(List<Advertisement> items) {
        updateImpressionsSum(items);
        updateItemsCount(items);
    }

    private void updateImpressionsSum(List<Advertisement> items) {
        long impressionsSum = executionContext.getLong("impressionsSum");
        impressionsSum += items.stream()
                .mapToLong(Advertisement::getImpressions)
                .sum();
        executionContext.putLong("impressionsSum", impressionsSum);
    }

    private void updateItemsCount(List<Advertisement> items) {
        long itemsCount = executionContext.getLong("itemsCount");
        itemsCount += items.size();
        executionContext.putLong("itemsCount", itemsCount);
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        printImpressionsData();
    }

    private void printImpressionsData() {
        ExecutionContext executionContext = jobExecution.getExecutionContext();
        Long impressionsSum = executionContext.getLong("impressionsSum");
        Long itemsCount = executionContext.getLong("itemsCount");
        String date = jobExecution.getJobParameters()
                .getString("date");

        System.out.println("날짜: " + date);
        System.out.println("데이터 수: " + itemsCount);
        System.out.println("총 노출수: " + impressionsSum);
        System.out.println("평균 노출수: " + impressionsSum / itemsCount);
    }
}
