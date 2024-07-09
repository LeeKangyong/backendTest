package kr.co.polycube.backendtest.config;

import java.time.LocalDateTime;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepExecutionListener implements StepExecutionListener {

    

    @Override
    public void beforeStep(StepExecution stepExecution) {
        
        LocalDateTime startTime = LocalDateTime.now();
        stepExecution.setStartTime(startTime); 
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
       
        LocalDateTime endTime = LocalDateTime.now();
        stepExecution.setEndTime(endTime); // LocalDateTime 사용
        return ExitStatus.COMPLETED;
    }
}