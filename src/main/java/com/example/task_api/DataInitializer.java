package com.example.task_api;

import com.example.task_api.model.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public DataInitializer(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize data if the database is empty
        if (taskRepository.count() == 0) {
            initializeTasks();
        }
    }

    private void initializeTasks() {
        Task[] tasks = {
                new Task(null, "Setup CI/CD Pipeline", "Configure GitHub Actions for automated deployment", "Pending",
                        LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5)),
                new Task(null, "Docker Configuration", "Create Dockerfile and docker-compose for the application",
                        "In Progress", LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(2)),
                new Task(null, "Database Migration", "Setup PostgreSQL database with proper schemas", "Completed",
                        LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(1)),
                new Task(null, "API Documentation", "Document all REST API endpoints using Swagger", "Pending",
                        LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2)),
                new Task(null, "Unit Testing", "Write comprehensive unit tests for all services", "In Progress",
                        LocalDateTime.now().minusDays(1), LocalDateTime.now().minusHours(12)),
                new Task(null, "Security Implementation", "Add authentication and authorization", "Pending",
                        LocalDateTime.now(), LocalDateTime.now()),
                new Task(null, "Frontend Integration", "Connect React frontend with the API", "Pending",
                        LocalDateTime.now().minusHours(6), LocalDateTime.now().minusHours(6)),
                new Task(null, "Performance Optimization", "Optimize database queries and API responses", "Pending",
                        LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(3)),
                new Task(null, "Monitoring Setup", "Implement application monitoring and logging", "Pending",
                        LocalDateTime.now().minusHours(1), LocalDateTime.now().minusHours(1)),
                new Task(null, "Production Deployment", "Deploy application to production environment", "Pending",
                        LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(30))
        };

        for (Task task : tasks) {
            taskRepository.save(task);
        }

        System.out.println("✅ Initialized database with 10 sample tasks!");
    }
}