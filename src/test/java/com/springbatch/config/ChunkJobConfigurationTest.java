package com.springbatch.config;

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
@SpringBootTest(classes = {ChunkJobConfiguration.class, TestBatchConfiguration.class})
class ChunkJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void chunkJob() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder().addLong("date", new Date().getTime())
                .toJobParameters();
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
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
                .isEqualTo(3 + 1);
        Assertions.assertThat(stepExecution.getReadCount())
                .isEqualTo(6);
        Assertions.assertThat(stepExecution.getWriteCount())
                .isEqualTo(6);
    }
}