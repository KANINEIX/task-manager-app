package org.example.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @JsonProperty("title")
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
