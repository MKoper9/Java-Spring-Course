package com.example.todoapp.adapter;

import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository,JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*)>0 from tasks where id=?1")
    boolean existsById(Integer id);

    @Override
    boolean existByDoneIsFalseAndGroup_Id(Integer groupId);
}