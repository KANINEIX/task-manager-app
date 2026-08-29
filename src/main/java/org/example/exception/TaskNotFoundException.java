package org.example.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(Integer id) {
        super("Task not found with id: " + id);
    }
}
