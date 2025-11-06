package com.example.task_api;

import com.example.task_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // JpaRepository provides findAll(), save(), findById(), deleteById() methods
    // for free.
}