package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.example.constant.PriorityType;
import org.example.constant.StatusType;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskEntity {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private StatusType status;

    @JsonProperty("priority")
    private PriorityType priority;

    @JsonProperty("created_datetime")
    private LocalDateTime createdDateTime;

    @JsonProperty("updated_dateTime")
    private LocalDateTime updatedDateTime;
}
