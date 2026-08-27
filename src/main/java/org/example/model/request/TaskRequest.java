package org.example.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class TaskRequest {
    @JsonProperty("title")
    @NonNull
    private String title;

    @JsonProperty("description")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String description;

    @JsonProperty("status")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String status;

    @JsonProperty("priority")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String priority;
}
