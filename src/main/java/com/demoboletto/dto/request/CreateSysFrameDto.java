package com.demoboletto.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "시스템에서 관리하는 프레임 추가")
@Getter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSysFrameDto {
    @Schema(description = "프레임의 이름", example = "프레임1")
    @NotNull(message = "frameName can not be null")
    @JsonProperty("frame_name")
    private String frameName;

    @Schema(description = "프레임의 설명", example = "프레임 설명입니다.")
    @NotNull(message = "description can not be null")
    @JsonProperty("description")
    private String description;

    @Schema(description = "프레임의 기본 제공 여부", example = "true", defaultValue = "false")
    @NotNull(message = "defaultProvided can not be null")
    @JsonProperty("default_provided")
    private boolean defaultProvided;

    @Schema(description = "프레임 코드", example = "FR")
    @NotNull(message = "frameCode can not be null")
    @JsonProperty("frame_code")
    private String frameCode;


}