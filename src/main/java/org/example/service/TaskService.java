package org.example.service;

import org.example.constant.ExceptionConstant;
import org.example.constant.PriorityType;
import org.example.constant.StatusType;
import org.example.exception.GlobalExceptionHandler;
import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicInteger idSequence = new AtomicInteger();
    private static final Logger log = (Logger) LoggerFactory.getLogger(TaskService.class);
    private final ExceptionConstant exc;

    private TaskService(TaskRepository taskRepository, ExceptionConstant exc) {
        this.taskRepository = taskRepository;
        this.exc = exc;
    }

    public List<TaskEntity> getTaskList() {
        log.info("Start get task list");
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        log.info("End get task list");
        return taskEntityList;
    }

    public List<TaskEntity> getTaskListByStatus(String status) {
        log.info("Start get task by status: [{}]", status);
        StatusType statusType = StatusType.valueOf(status);
        List<TaskEntity> taskEntityList = taskRepository.findByStatus(statusType);
        log.info("End get task by status: [{}]", status);
        return taskEntityList;
    }

    public Optional<TaskEntity> getTaskById(Integer id) {
        log.info("Start get task by id: [{}]", id);
        Optional<TaskEntity> taskResponse = taskRepository.findById(id);
        log.info("End get task by id: [{}]", id);
        return taskResponse;
    }

    public TaskEntity createTask(TaskRequest taskRequest) {
        log.info("Start create task by title: [{}]", taskRequest.getTitle());
        TaskEntity taskEntity = TaskEntity.builder()
                .id(idSequence.getAndIncrement())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(StatusType.valueOf(taskRequest.getStatus()))
                .priority(PriorityType.valueOf(taskRequest.getPriority()))
                .build();
        TaskEntity taskResponse = taskRepository.save(taskEntity);
        log.info("End create task by title: [{}] and id: [{}]", taskResponse.getTitle(), taskResponse.getId());
        return taskResponse;
    }

    public TaskEntity editTask(Integer id, TaskRequest taskRequest) throws GlobalExceptionHandler {
        log.info("Start edit task by id: [{}]", id);
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
        log.info("Start edit task by id: [{}]", id);
        return taskRepository.save(newEdited);
    }

    public void deleteTask(Integer id) {
        log.info("Start delete task by id: [{}]", id);
        taskRepository.deleteById(id);
        log.info("End delete task by id: [{}]", id);
    }
}
