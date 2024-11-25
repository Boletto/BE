package com.demoboletto.dto.response;


import com.demoboletto.domain.SysFrame;
import com.demoboletto.domain.UserFrame;
import com.demoboletto.dto.global.BaseTimeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Schema(description = "Usable frame information")
@Getter
@SuperBuilder
public class GetUserUsableFrameDto extends BaseTimeDto {
    @Schema(description = "프레임의 기본키", example = "1")
    private long frameId;

    @Schema(description = "프레임의 이름", example = "프레임1")
    private String frameName;

    @Schema(description = "프레임의 설명", example = "프레임 설명입니다.")
    private String description;

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
                .isOwned(false)
                .frameCode(sysFrame.getFrameCode())
                .frameUrl(sysFrame.getFrameUrl())
                .createdDate(sysFrame.getCreatedDate())
                .modifiedDate(sysFrame.getModifiedDate())
                .build();
    }

    public static GetUserUsableFrameDto of(UserFrame userFrame) {
        return GetUserUsableFrameDto.builder()
                .frameId(userFrame.getFrame().getFrameId())
                .frameName(userFrame.getFrame().getFrameName())
                .description(userFrame.getFrame().getDescription())
                .isOwned(true)
                .frameCode(userFrame.getFrame().getFrameCode())
                .frameUrl(userFrame.getFrame().getFrameUrl())
                .createdDate(userFrame.getCreatedDate())
                .modifiedDate(userFrame.getModifiedDate())
                .build();
    }


}