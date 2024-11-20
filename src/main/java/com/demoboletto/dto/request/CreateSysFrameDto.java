package com.demoboletto.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "시스템에서 관리하는 프레임 추가")
@Getter
public class CreateSysFrameDto {
    @Schema(description = "프레임의 이름", example = "프레임1")
    @NotNull(message = "frameName can not be null")
    private String frameName;

    @Schema(description = "프레임의 설명", example = "프레임 설명입니다.")
    @NotNull(message = "description can not be null")
    private String description;

    @Schema(description = "프레임의 기본 제공 여부", example = "true", defaultValue = "false")
    @NotNull(message = "defaultProvided can not be null")
    private boolean defaultProvided;

    @Schema(description = "프레임 코드", example = "FR")
    @NotNull(message = "frameCode can not be null")
    private String frameCode;

    @Schema(description = "프레임 이미지 파일")
    @NotNull(message = "file can not be null")
    private MultipartFile file;

}