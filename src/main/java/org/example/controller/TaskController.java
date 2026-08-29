package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.exception.TaskNotFoundException;
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
@AllArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private TaskService taskService;

    private static final Logger log = (Logger) LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/get")
    public List<TaskEntity> getTaskList(
            @RequestParam(name = "status", required = false) String status
    ) {
        log.info("Start Get Task List");
        if (status != null && !status.isBlank()) {
            return taskService.getTaskListByStatus(status);
        }
        log.info("End Get Task List");
        return taskService.getTaskList();
    }

    @GetMapping("/get/{id}")
    public TaskEntity getTaskListById(@PathVariable("id") Integer id) throws TaskNotFoundException {
        log.info("Start Get Task List by id: [{}]", id);
        TaskEntity taskResponse = taskService.getTaskById(id);
        log.info("End Get Task List by id: [{}]", id);
        return taskResponse;
    }

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestBody TaskRequest taskRequest) {
        log.info("Start create task by title: [{}]", taskRequest.getTitle());
        TaskEntity taskEntity = taskService.createTask(taskRequest);
        log.info("End create task by title: [{}]", taskRequest.getTitle());
        return ResponseEntity.ok(taskEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity editTask(@PathVariable("id") Integer id, @RequestBody TaskRequest taskRequest) throws TaskNotFoundException {
        log.info("Start edit task by id: [{}]", id);
        TaskEntity taskEntity = taskService.editTask(id, taskRequest);
        log.info("End edit task by id: [{}]", id);
        return ResponseEntity.ok(taskEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Integer id) throws TaskNotFoundException {
        log.info("Start delete task by id: [{}]", id);
        taskService.deleteTask(id);
        log.info("End delete task by id: [{}]", id);
        return ResponseEntity.ok(TaskEntity.class);
    }
}
