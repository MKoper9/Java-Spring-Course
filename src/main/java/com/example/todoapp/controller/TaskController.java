package com.example.todoapp.controller;

import com.example.todoapp.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RepositoryRestController
public class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    @Autowired
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }


    @GetMapping( path = "/tasks")
    ResponseEntity<?> readAllTasks(){
        logger.warn("Exposing all tasks!");
        return ResponseEntity.ok(repository.findAll());
    }


}
