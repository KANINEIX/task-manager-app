package org.example.repository;

import org.example.constant.StatusType;
import org.example.model.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TaskRepository {

    private final Map<Integer, TaskEntity> storage = new ConcurrentHashMap<>();

    public List<TaskEntity> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<TaskEntity> findById(Integer id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<TaskEntity> findByStatus(StatusType statusType) {
        return new ArrayList<>(storage.values()
                .stream()
                .filter(taskEntity ->
                        taskEntity.getStatus().equals(statusType)
                ).toList());
    }

    public TaskEntity save(TaskEntity taskEntity) {
        storage.put(taskEntity.getId(), taskEntity);
        return taskEntity;
    }

    public void deleteById(Integer id) {
        storage.remove(id);
    }
}
