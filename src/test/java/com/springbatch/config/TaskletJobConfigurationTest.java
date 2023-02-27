package com.springbatch.config;

import com.springbatch.TestBatchConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBatchTest
@SpringBootTest(classes = {TaskletJobConfiguration.class, TestBatchConfiguration.class})
class TaskletJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    @Test
    void taskletJob() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder().addLong("date", new Date().getTime())
                .toJobParameters();
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        StepExecution stepExecution = (StepExecution) ((List) jobExecution.getStepExecutions()).get(0);
        // then
        Assertions.assertThat(jobExecution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);
        Assertions.assertThat(jobExecution.getExitStatus())
                .isEqualTo(ExitStatus.COMPLETED);
        Assertions.assertThat(stepExecution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);
        Assertions.assertThat(stepExecution.getExitStatus())
                .isEqualTo(ExitStatus.COMPLETED);
        Assertions.assertThat(stepExecution.getCommitCount())
                .isEqualTo(1);
    }
}