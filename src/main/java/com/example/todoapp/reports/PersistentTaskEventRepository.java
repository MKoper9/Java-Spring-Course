package com.example.todoapp.reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PersistentTaskEventRepository extends JpaRepository<PersistedTaskEvent, Integer> {
    List<PersistedTaskEvent> findByTaskId(int id);
}
