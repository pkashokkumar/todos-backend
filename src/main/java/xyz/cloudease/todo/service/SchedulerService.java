package xyz.cloudease.todo.service;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.cloudease.todo.model.Todo;
import xyz.cloudease.todo.persistence.entity.TodoEntity;

import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class SchedulerService {

    private Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(TodoEntity todo) throws SchedulerException {
        Trigger trigger = buildTrigger(todo);
        this.scheduler.scheduleJob(trigger);
    }

    private Trigger buildTrigger(TodoEntity todo) {
       return newTrigger()
                .withIdentity("Phone_Call_Trigger_" + todo.getId(), null)
                .startAt(todo.getTargetDate())
                .forJob("Phone_Call_Job", null)
                .usingJobData("message", todo.getDescription())
                .build();
    }
}
