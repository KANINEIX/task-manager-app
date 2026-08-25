package org.example.controller;

import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private TaskService taskService;

    @GetMapping("/get")
    public List<TaskEntity> getTaskList() {
        return null;
    }

    @GetMapping("/get")
    public List<TaskEntity> getTaskListByStatus(@RequestParam("status") String status) {
        return null;
    }

    @GetMapping("/get/{id}")
    public List<String> getTaskListById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody TaskRequest task) {
        return ResponseEntity.ok(TaskEntity.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity editTask(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(TaskEntity.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(TaskEntity.class);
    }
}
