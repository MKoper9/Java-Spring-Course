package com.example.todoapp.reports;

import com.example.todoapp.model.event.TaskDone;
import com.example.todoapp.model.event.TaskEvent;
import com.example.todoapp.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ChangedTaskEventListener {
    private static final Logger loggr = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistentTaskEventRepository repository;

    public ChangedTaskEventListener(PersistentTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    void on(TaskDone event){
        onChanged(event);
    }

    @EventListener
    void on(TaskUndone event){
        onChanged(event);
    }

    private void onChanged(TaskEvent event) {
        loggr.info("Got" +event);
        repository.save(new PersistedTaskEvent(event));
    }
}
