package com.example.restapi.config;

import com.example.restapi.model.Employee;
import com.example.restapi.processor.DataProcessor;
import com.example.restapi.reader.DataReader;
import com.example.restapi.writer.DataWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {
    @Autowired
    private DataReader dataReader;
    @Autowired
    private DataWriter dataWriter;
    @Autowired
    private DataProcessor dataProcessor;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Bean
    public Job reatApiJob(JobRepository jobRepository) {
        return new JobBuilder("myJob", jobRepository)
                .start(sampleStep(jobRepository))
                .build();
    }
    @Bean
    public Step sampleStep(JobRepository jobRepository) {
        return new StepBuilder("sampleStep", jobRepository)
                .<Employee, Employee>chunk(2, transactionManager)
                .reader(dataReader)
                .processor(dataProcessor)
                .writer(dataWriter)
                .build();
    }
}
