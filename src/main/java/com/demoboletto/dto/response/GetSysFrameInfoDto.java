package com.demoboletto.dto.response;


import com.demoboletto.domain.SysFrame;
import com.demoboletto.dto.global.BaseTimeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Schema(description = "프레임 정보")
@Getter
@SuperBuilder
public class GetSysFrameInfoDto extends BaseTimeDto {
    @Schema(description = "프레임의 기본키", example = "1")
    private long frameId;

    @Schema(description = "프레임의 이름", example = "프레임1")
    private String frameName;

    @Schema(description = "프레임의 설명", example = "프레임 설명입니다.")
    private String description;

    @Schema(description = "스티커의 기본 제공 여부", example = "true")
    private boolean defaultProvided;

    @Schema(description = "프레임 코드", example = "FRAME1")
    private String frameCode;

    @Schema(description = "프레임 이미지 URL", example = "https://example.com/frame.jpg")
    private String frameUrl;

    public static GetSysFrameInfoDto of(SysFrame sysFrame) {
        return GetSysFrameInfoDto.builder()
                .frameId(sysFrame.getFrameId())
                .frameName(sysFrame.getFrameName())
                .description(sysFrame.getDescription())
                .frameCode(sysFrame.getFrameCode())
                .frameUrl(sysFrame.getFrameUrl())
                .createdDate(sysFrame.getCreatedDate())
                .modifiedDate(sysFrame.getModifiedDate())
                .defaultProvided(sysFrame.isDefaultProvided())
                .build();
    }
}