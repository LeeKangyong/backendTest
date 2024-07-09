package kr.co.polycube.backendtest.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.polycube.backendtest.service.LottoBatchService;
import kr.co.polycube.backendtest.config.CustomStepExecutionListener;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class LottoBatchConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private LottoBatchService lottoBatchService;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job lottoJob() {
        return new JobBuilder("lottoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet(lottoBatchService, transactionManager)
                .listener(new CustomStepExecutionListener())
                .build();
    }

    @Scheduled(cron = "0 0 0 * * SUN") 
    // 단위 테스트 시 사용할 것
    //@Scheduled(cron = "0 * * * * *") 
    public void perform() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(lottoJob(), jobParameters);
    }
}