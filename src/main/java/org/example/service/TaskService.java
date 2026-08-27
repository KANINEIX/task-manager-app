package org.example.service;

import org.example.enums.PriorityType;
import org.example.enums.StatusType;
import org.example.model.TaskEntity;
import org.example.model.request.TaskRequest;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

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
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(StatusType.valueOf(taskRequest.getStatus()))
                .priority(PriorityType.valueOf(taskRequest.getPriority()))
                .build();
        return taskRepository.save(taskEntity);
    }
}
