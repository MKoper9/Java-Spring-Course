package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskService;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;
    private final TaskService service;

    @Autowired
    TaskController(final TaskRepository repository, TaskService service) {
        this.repository = repository;
        this.service = service;
    }


    @RequestMapping(method = RequestMethod.GET, params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        logger.warn("Exposing all tasks!");
        return service.findAllAsync().thenApply(ResponseEntity::ok);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pager");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ResponseEntity<Task> getTask(@PathVariable int id) {
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        Task result = repository.save(task);
        return ResponseEntity.created(URI.create("/" + result.getId())).build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity
                .ok(repository.findByDone(state));
    }

}
