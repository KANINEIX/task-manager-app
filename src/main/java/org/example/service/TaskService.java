package org.example.service;

import lombok.AllArgsConstructor;
import org.example.constant.PriorityType;
import org.example.constant.StatusType;
import org.example.exception.TaskNotFoundException;
import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicInteger idSequence = new AtomicInteger();
    private static final Logger log = (Logger) LoggerFactory.getLogger(TaskService.class);

    public List<TaskEntity> getTaskList() {
        log.info("Start get task list");
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        log.info("End get task list");
        return taskEntityList;
    }

    public List<TaskEntity> getTaskListByStatus(String status) {
        log.info("Start get task by status: [{}]", status);
        StatusType statusType = StatusType.valueOf(status.trim().toUpperCase());
        List<TaskEntity> taskEntityList = taskRepository.findByStatus(statusType);
        log.info("End get task by status: [{}]", status);
        return taskEntityList;
    }

    public TaskEntity getTaskById(Integer id) throws TaskNotFoundException {
        log.info("Start get task by id: [{}]", id);
        TaskEntity taskResponse = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        log.info("End get task by id: [{}]", id);
        return taskResponse;
    }

    public TaskEntity createTask(TaskRequest taskRequest) {
        log.info("Start create task by title: [{}]", taskRequest.getTitle());
        TaskEntity taskEntity = TaskEntity.builder()
                .id(idSequence.incrementAndGet())
                .title(taskRequest.getTitle())
                .priority(
                        taskRequest.getPriority() != null ?
                                PriorityType.valueOf(taskRequest.getPriority().trim().toUpperCase()) :
                                PriorityType.LOW
                )
                .status(
                        taskRequest.getStatus() != null ?
                                StatusType.valueOf(taskRequest.getStatus().trim().toUpperCase()) :
                                StatusType.TODO
                )
                .description(
                        taskRequest.getDescription() != null ?
                                taskRequest.getDescription() :
                                ""
                )
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .build();
        TaskEntity taskResponse = taskRepository.save(taskEntity);
        log.info("End create task by title: [{}] and id: [{}]", taskResponse.getTitle(), taskResponse.getId());
        return taskResponse;
    }

    public TaskEntity editTask(Integer id, TaskRequest taskRequest) throws TaskNotFoundException {
        log.info("Start edit task by id: [{}]", id);
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
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
                                PriorityType.valueOf(taskRequest.getPriority().trim().toUpperCase()) :
                                taskEntity.getPriority()
                )
                .status(
                        taskRequest.getStatus() != null ?
                                StatusType.valueOf(taskRequest.getStatus().trim().toUpperCase()) :
                                taskEntity.getStatus()
                )
                .description(
                        taskRequest.getDescription() != null ?
                                taskRequest.getDescription() :
                                taskEntity.getDescription()
                )
                .createdDateTime(taskEntity.getCreatedDateTime())
                .updatedDateTime(LocalDateTime.now())
                .build();
        log.info("Start edit task by id: [{}]", id);
        return taskRepository.save(newEdited);
    }

    public void deleteTask(Integer id) throws TaskNotFoundException {
        log.info("Start delete task by id: [{}]", id);
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
        log.info("End delete task by id: [{}]", id);
    }
}
