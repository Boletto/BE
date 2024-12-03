package com.demoboletto.dto.response;


import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.dto.global.BaseTimeDto;
import com.demoboletto.type.EFrameType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Schema(description = "Usable frame information")
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class GetUserUsableFrameDto extends BaseTimeDto {
    @Schema(description = "프레임의 기본키", example = "1")
    private long frameId;

    @Schema(description = "프레임의 이름", example = "프레임1")
    private String frameName;

    @Schema(description = "프레임의 설명", example = "프레임 설명입니다.")
    private String description;

    @Schema(description = "프레임 타입", example = "SYSTEM, CUSTOM")
    @Enumerated(EnumType.STRING)
    private EFrameType frameType;

    @Schema(description = "프레임의 소유 여부", example = "true")
    private boolean isOwned;

    @Schema(description = "프레임 코드", example = "FRAME1")
    private String frameCode;

    @Schema(description = "프레임 이미지 URL", example = "https://example.com/frame.jpg")
    private String frameUrl;

    public static GetUserUsableFrameDto of(SysFrame sysFrame) {
        return GetUserUsableFrameDto.builder()
                .frameId(sysFrame.getFrameId())
                .frameName(sysFrame.getFrameName())
                .description(sysFrame.getDescription())
                .frameType(EFrameType.SYSTEM)
                .isOwned(false)
                .frameCode(sysFrame.getFrameCode())
                .frameUrl(sysFrame.getFrameUrl())
                .createdDate(sysFrame.getCreatedDate())
                .modifiedDate(sysFrame.getModifiedDate())
                .build();
    }

    public static GetUserUsableFrameDto of(UserFrame userFrame) {
        GetUserUsableFrameDtoBuilder<?, ?> builder = GetUserUsableFrameDto.builder();
        if (userFrame.getFrameType() == EFrameType.CUSTOM) {
            builder.frameId(userFrame.getCustomFrame().getFrameId())
                    .frameType(EFrameType.CUSTOM)
                    .isOwned(true)
                    .frameCode(userFrame.getCustomFrame().getFrameCode())
                    .frameUrl(userFrame.getCustomFrame().getFrameUrl());
        } else {
            builder.frameId(userFrame.getSysFrame().getFrameId())
                    .frameName(userFrame.getSysFrame().getFrameName())
                    .description(userFrame.getSysFrame().getDescription())
                    .frameType(EFrameType.SYSTEM)
                    .isOwned(true)
                    .frameCode(userFrame.getSysFrame().getFrameCode())
                    .frameUrl(userFrame.getSysFrame().getFrameUrl());
        }
        return builder
                .createdDate(userFrame.getCreatedDate())
                .modifiedDate(userFrame.getModifiedDate())
                .build();
    }


}