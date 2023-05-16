package com.springbasics.task;

import com.springbasics.task.dto.CreateTaskDTO;
import com.springbasics.task.dto.UpdateTaskDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
        ArrayList<Task> taskList = taskService.getAllTask();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        Task task = taskService.createTask(createTaskDTO.getName(), createTaskDTO.getDueDate());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") int taskId, @RequestBody UpdateTaskDTO updateTaskDTO) {
       Task task = taskService.updateTask(taskId, updateTaskDTO.getDueDate(), updateTaskDTO.getCompleted());
       return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") int taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    public ResponseEntity<String> taskNotFoundExceptionHandler(TaskService.TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(TaskService.InvalidInputException.class)
    public ResponseEntity<String> invalidInputExceptionHandler(TaskService.InvalidInputException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
