package org.example.controller;

import org.example.exception.GlobalExceptionHandler;
import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private TaskService taskService;

    private static final Logger log = (Logger) LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/get")
    public List<TaskEntity> getTaskList(
            @RequestParam(name = "status", required = false) String status
    ) throws GlobalExceptionHandler {
        log.info("Start Get Task List");
        if (status != null && !status.isBlank()) {
            return taskService.getTaskListByStatus(status);
        }
        log.info("End Get Task List");
        return taskService.getTaskList();
    }

    @GetMapping("/get/{id}")
    public Optional<TaskEntity> getTaskListById(@PathVariable("id") Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody TaskRequest task) {
        TaskEntity taskEntity = taskService.createTask(task);
        return ResponseEntity.ok(taskEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity editTask(@PathVariable("id") Integer id, @RequestBody TaskRequest taskRequest) throws GlobalExceptionHandler {
        return ResponseEntity.ok(taskService.editTask(id, taskRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(TaskEntity.class);
    }
}
