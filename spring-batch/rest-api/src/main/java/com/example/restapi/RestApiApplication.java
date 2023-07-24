package com.example.restapi;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/job")
public class RestApiApplication implements CommandLineRunner {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job reatApiJob;
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		JobParameters jobParameters = new JobParametersBuilder()
//				.addString("jobId", String.valueOf(System.currentTimeMillis()))
//				.toJobParameters();
//		jobLauncher.run(reatApiJob, jobParameters);
	}

	@GetMapping
	public String job() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("jobId", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(reatApiJob, jobParameters);
		return "SUCCESS";
	}

}
