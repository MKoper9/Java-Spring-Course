package com.example.todoapp;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskGroup;
import com.example.todoapp.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    public Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if(!groupRepository.existByDescription(description)){
            logger.info("No required group found! Adding it!");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextColsedEvent", null, group),
                    new Task("ContextRefreshedEvent", null, group),
                    new Task("ContextStoppedEvent", null, group),
                    new Task("ContextStartedEvent", null, group)
            ));
            groupRepository.save(group);
        }
    }
}
