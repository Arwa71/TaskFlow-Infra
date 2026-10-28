package com.taskflow.service;

import com.taskflow.model.Task;
import com.taskflow.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    // First call reads PostgreSQL, next calls are served from Redis
    @Cacheable(value = "tasks", key = "#id")
    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    }

    public Task create(Task task) {
        return repository.save(task);
    }

    // Remove the old cached value so users never see stale data
    @CacheEvict(value = "tasks", key = "#id")
    public Task update(Long id, Task data) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
        task.setTitle(data.getTitle());
        task.setDescription(data.getDescription());
        task.setCompleted(data.isCompleted());
        return repository.save(task);
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
