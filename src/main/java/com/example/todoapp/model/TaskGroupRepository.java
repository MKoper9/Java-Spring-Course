package com.example.todoapp.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup>findAll();

    Optional<TaskGroup>findById(Integer id);
    TaskGroup save(TaskGroup entity);

    boolean existByDoneIsFalseAndProject_Id(Integer projectId);

    boolean existByDescription(String description);
}
