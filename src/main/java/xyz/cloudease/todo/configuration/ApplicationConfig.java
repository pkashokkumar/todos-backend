package xyz.cloudease.todo.configuration;

import org.modelmapper.ModelMapper;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.cloudease.todo.scheduling.PhoneCallJob;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JobDetail phoneCallJobDetail() {
        return JobBuilder.newJob().ofType(PhoneCallJob.class)
                .storeDurably()
                .withIdentity("Phone_Call_Job")
                .withDescription("Invoke Phone call service")
                .build();
    }
}
