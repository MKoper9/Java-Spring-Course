package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.*;
import com.example.todoapp.model.projection.GroupReadModel;
import com.example.todoapp.model.projection.GroupTaskWriteModel;
import com.example.todoapp.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskConfigurationProperties config;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService service;

    public ProjectService(ProjectRepository projectRepository, TaskConfigurationProperties config, TaskGroupRepository taskGroupRepository, TaskGroupService service) {
        this.projectRepository = projectRepository;
        this.config = config;
        this.taskGroupRepository = taskGroupRepository;
        this.service = service;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public GroupReadModel createGroup( LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undon group from project is allowed");
        }

        return projectRepository.findById(projectId)
                .map(project -> {
                    GroupWriteModel targetGroup;
                    targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new GroupTaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                projectStep.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toSet())
                    );
                    service.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

}
