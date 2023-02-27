package com.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class TaskletJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job taskletJob() {
        return jobBuilderFactory.get("taskletJob")
                .incrementer(new RunIdIncrementer())
                .start(taskletStep())
                .build();
    }

    @Bean
    public Step taskletStep() {
        return stepBuilderFactory.get("taskletStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("taskletStep has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
