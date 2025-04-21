package com.demoboletto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecordUserActionDto(
        @JsonProperty("action_type")
        String actionType,
        @JsonProperty("action_details")
        String actionDetails
) {
}
