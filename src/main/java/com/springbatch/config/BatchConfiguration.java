package com.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job1() {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }

    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<String, String>chunk(3)
                .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6")))
                .writer(items -> System.out.println(items))
                .build();
    }

    @Bean
    public Job job2() {
        return jobBuilderFactory.get("job2")
                .incrementer(new RunIdIncrementer())
                .start(taskletStep())
                .build();

    }

    private Step taskletStep() {
        return stepBuilderFactory.get("taskletStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("taskletStep has executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
