package com.springbatch.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBatchTest
@SpringBootTest(classes = {ChunkJobConfiguration.class, TestBatchConfiguration.class})
class ChunkJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void chunkJob() throws Exception {
        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        // then
        Assertions.assertThat(jobExecution.getStatus())
                .isEqualTo(BatchStatus.COMPLETED);
        Assertions.assertThat(jobExecution.getExitStatus())
                .isEqualTo(ExitStatus.COMPLETED);
    }
}