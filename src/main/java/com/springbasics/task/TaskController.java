package com.springbasics.task;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private ArrayList<Task> taskList = new ArrayList<>();
    private int currentTaskId = 1;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTask() {
        //return taskList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") int taskId) {

        Optional<Task> optionalTask = taskList.stream()
                .filter(t -> t.getId() == taskId)
                .findFirst();

        if(optionalTask.isPresent()) {
            return ResponseEntity.status(404).body(optionalTask.get());;
        } else {
            //return ResponseEntity.status(404).body("Task not found");
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        if(task.getName().isEmpty()) {
            return ResponseEntity.status(400).body();
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate taskDueDate = task.getDueDate();

        if(taskDueDate.isBefore(currentDate)) {
            return "Enter duedate after currentdate";//TODO 400
        }

        task.setId(currentTaskId);
        taskList.add(task);
        currentTaskId++;

        return "Task Created";
    }



    @PatchMapping("/{id}")
    public String updateTask(@RequestBody Task task, @PathVariable("id") int taskId) {

        Optional<Task> taskToBeUpdatedOptional = taskList.stream().filter(t -> t.getId() == taskId).findFirst();

        if(taskToBeUpdatedOptional.isPresent()) {
            Task taskTobeUpdated = taskToBeUpdatedOptional.get();

            LocalDate currentDate = LocalDate.now();
            LocalDate taskDueDate = task.getDueDate();

            if(taskDueDate.isBefore(currentDate)) {
                return "Enter duedate after currentdate";//TODO 400
            }

            taskTobeUpdated.setDueDate(task.getDueDate());
            taskTobeUpdated.setCompleted(task.isCompleted());

            return "Task updated";
        }

        return "Task not found";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") int taskId) {

        // TODO can't remove elements from arraylist without index

        return "Task Deleted";
    }
}
