package xyz.cloudease.todo.scheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class PhoneCallJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=============== " + new Date() +
                ": " + jobExecutionContext.getMergedJobDataMap().get("message") +" =====");
    }
}
