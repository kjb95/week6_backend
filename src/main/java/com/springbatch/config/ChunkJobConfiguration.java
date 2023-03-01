package com.springbatch.config;

import com.springbatch.batch.process.CustomItemProcess;
import com.springbatch.entity.Advertisement;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
public class ChunkJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }

    @Bean
    public Step chunkStep() {
        return stepBuilderFactory.get("chunkStep")
                .<Advertisement, Advertisement>chunk(1000)
                .reader(itemReader())
                .processor(itemProcess(null))
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public CustomItemProcess itemProcess(@Value("#{jobParameters[date]}") String date) {
        return new CustomItemProcess(date);
    }

    @Bean
    public JpaItemWriter<Advertisement> itemWriter() {
        return new JpaItemWriterBuilder<Advertisement>().entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

    @Bean
    public FlatFileItemReader itemReader() {
        return new FlatFileItemReaderBuilder<Advertisement>().name("test")
                .resource(new ClassPathResource("/sample.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Advertisement.class)
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("afCode", "afCodeName", "costSource", "adType", "campaign", "subCampaign", "device", "channel", "mediaName", "productName", "brand", "exhibitionNumber", "department", "keyword", "period", "impressions")
                .build();
    }
}
