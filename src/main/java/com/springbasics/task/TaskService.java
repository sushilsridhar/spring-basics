package com.springbasics.task;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private List<Task> taskList = new ArrayList<>();
    private int currentTaskId = 1;

    public List<Task> getAllTask(TaskFilter taskFilter) {

        if(taskFilter != null) {
            List<Task> filteredList = taskList.stream()
                    .filter(task -> {
                        if (taskFilter.beforeDate != null && task.getDueDate().after(taskFilter.beforeDate)) {
                            return false;
                        }
                        if (taskFilter.afterDate != null && task.getDueDate().before(taskFilter.afterDate)) {
                            return false;
                        }
                        if (taskFilter.completed != null && task.getCompleted() != taskFilter.completed) {
                            return false;
                        }
                        return true;
                    }).collect(Collectors.toList());

            if(taskFilter.sort != null && !taskFilter.sort.isEmpty()) {
                if (taskFilter.sort.equalsIgnoreCase("AESC")) {
                    filteredList.sort((t1, t2) -> {
                        if (t1.getDueDate().equals(t2.getDueDate())) {
                            return 0;
                        } else if (t1.getDueDate().after(t2.getDueDate())) {
                            return 1;
                        }
                        return -1;
                    });
                } else if (taskFilter.sort.equalsIgnoreCase("DESC")) {
                    filteredList.sort((t1, t2) -> {
                        if (t1.getDueDate().equals(t2.getDueDate())) {
                            return 0;
                        } else if (t1.getDueDate().after(t2.getDueDate())) {
                            return -1;
                        }
                        return 1;
                    });
                }
            }
            return filteredList;
        }
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

        if(dueDate != null) {
            isDueDateValid(dueDate);
            task.setDueDate(dueDate);
        }

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