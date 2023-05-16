package com.springbasics.task;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    private ArrayList<Task> taskList = new ArrayList<>();
    private int currentTaskId = 1;

    public ArrayList<Task> getAllTask() {
        return taskList;
    }

    public Task getTaskById(int taskId) {
        Optional<Task> optionalTask = taskList.stream()
                .filter(t -> t.getId() == taskId)
                .findFirst();

        if(optionalTask.isPresent()) {
            return optionalTask.get();
        }

        throw new TaskNotFoundException(taskId);
    }

    public Task createTask(String name, Date dueDate) {

        if(name.isEmpty() || name.length() < 5 || name.length() > 100) {
            throw new InvalidInputException("Invalid task name");
        }

        isDueDateValid(dueDate);

        Task task = new Task(currentTaskId++, name, dueDate, false);
        taskList.add(task);
        return task;
    }

    public Task updateTask(int taskId, Date dueDate, Boolean completed) {
        Task task = getTaskById(taskId);

        isDueDateValid(dueDate);
        task.setDueDate(dueDate);

        if(completed != null) {
            task.setCompleted(completed);
        }

        return task;
    }

    public void deleteTask(int taskId) {
        Task task = getTaskById(taskId);
        taskList.remove(task);
    }

    private void isDueDateValid(Date dueDate) {
        if(dueDate == null || dueDate.before(new Date())) {
            throw new InvalidInputException("Invalid Due Date");
        }
    }

    class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(int taskId) {
            super("Task with id " + taskId + " not found");
        }
    }

    class InvalidInputException extends IllegalArgumentException {
        public InvalidInputException(String message) {
            super(message);
        }
    }
}
