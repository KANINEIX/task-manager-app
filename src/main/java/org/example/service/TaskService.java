package org.example.service;

import org.example.constant.ExceptionConstant;
import org.example.constant.PriorityType;
import org.example.constant.StatusType;
import org.example.exception.GlobalExceptionHandler;
import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicInteger idSequence = new AtomicInteger();
    private final ExceptionConstant exc;

    private TaskService(TaskRepository taskRepository, ExceptionConstant exc) {
        this.taskRepository = taskRepository;
        this.exc = exc;
    }

    public List<TaskEntity> getTaskList() {
        return taskRepository.findAll();
    }

    public List<TaskEntity> getTaskListByStatus(String status) {
        StatusType statusType = StatusType.valueOf(status);
        return taskRepository.findByStatus(statusType);
    }

    public Optional<TaskEntity> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    public TaskEntity createTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .id(idSequence.getAndIncrement())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(StatusType.valueOf(taskRequest.getStatus()))
                .priority(PriorityType.valueOf(taskRequest.getPriority()))
                .build();
        return taskRepository.save(taskEntity);
    }

    public TaskEntity editTask(Integer id, TaskRequest taskRequest) throws GlobalExceptionHandler {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(
                GlobalExceptionHandler::new
        );
        TaskEntity newEdited = TaskEntity.builder()
                .id(taskEntity.getId())
                .title(
                        taskRequest.getTitle() != null ?
                                taskRequest.getTitle() :
                                taskEntity.getTitle()
                )
                .priority(
                        taskRequest.getPriority() != null ?
                                PriorityType.valueOf(taskRequest.getPriority()) :
                                taskEntity.getPriority()
                )
                .status(
                        taskRequest.getStatus() != null ?
                                StatusType.valueOf(taskRequest.getStatus()) :
                                taskEntity.getStatus()
                )
                .description(
                        taskRequest.getDescription() != null ?
                                taskRequest.getDescription() :
                                taskEntity.getDescription()
                )
                .build();
        return taskRepository.save(newEdited);
    }
}
