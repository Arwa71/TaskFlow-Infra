package com.taskflow;

import com.taskflow.model.Task;
import com.taskflow.repository.TaskRepository;
import com.taskflow.service.TaskService;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private final TaskRepository repo = mock(TaskRepository.class);
    private final TaskService service = new TaskService(repo);

    @Test
    void findByIdReturnsTask() {
        Task t = new Task();
        t.setTitle("Learn Docker");
        when(repo.findById(1L)).thenReturn(Optional.of(t));

        assertEquals("Learn Docker", service.findById(1L).getTitle());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.findById(99L));
    }
}
