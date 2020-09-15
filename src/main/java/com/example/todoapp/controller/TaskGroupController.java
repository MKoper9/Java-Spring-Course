package com.example.todoapp.controller;

import com.example.todoapp.logic.TaskGroupService;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupWriteModel;
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

@RestController
@RequestMapping(value = "/groups")
public class TaskGroupController {
    public static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository repository;

    @Autowired
    public TaskGroupController(TaskGroupService service, TaskRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        return ResponseEntity.created(URI.create("/")).body(service.createGroup(toCreate));
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }


    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toogleGroup(@PathVariable int id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
}
