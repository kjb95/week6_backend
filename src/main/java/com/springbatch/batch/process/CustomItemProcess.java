package com.springbatch.batch.process;

import com.springbatch.entity.Advertisement;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcess implements ItemProcessor<Advertisement, Advertisement> {
    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public Advertisement process(Advertisement item) throws Exception {
        String date = jobExecution.getJobParameters()
                .getString("date");

        if (date != null && !date.equals(item.getPeriod())) {
            return null;
        }
        return item;
    }
}