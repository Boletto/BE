package com.demoboletto.controller;

import com.demoboletto.annotation.UserId;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.response.FriendCodeDto;
import com.demoboletto.dto.response.FriendResponseDto;
import com.demoboletto.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Friend", description = "친구 관련 API")
@RequestMapping("/api/v1/friend")
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    @Operation(summary = "친구 목록 조회", description = "유저의 아이디를 넘겨받아 해당 유저의 친구 목록을 조회합니다.")
    @Schema(name = "Get Friend List", description = "친구 목록 조회")
    public ResponseDto<List<FriendResponseDto>> getFriends(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(friendService.getAllFriends(userId));
    }

    @GetMapping("/search")
    @Operation(summary = "친구 검색", description = "친구의 닉네임과 이름으로 친구를 조회합니다.")
    @Schema(name = "Search Friend", description = "특정 친구 조회")
    public ResponseDto<List<FriendResponseDto>> searchFriends(@Parameter(hidden = true) @UserId Long userId, @RequestParam String keyword) {
        return ResponseDto.ok(friendService.searchFriends(userId, keyword));
    }


    @DeleteMapping("/{friendId}")
    @Operation(summary = "친구 삭제", description = "삭제하고 싶은 친구의 유저 id를 받아 특정 친구를 삭제합니다.")
    @Schema(name = "Search Friend", description = "특정 친구 삭제")
    public ResponseDto<?> deleteFriend(@Parameter(hidden = true) @UserId Long userId, @PathVariable Long friendId) {
        friendService.deleteFriend(userId, friendId);
        return ResponseDto.ok("특정 친구 삭제에 성공하였습니다.");
    }

    @GetMapping("/code")
    @Operation(summary = "친구 코드 발급", description = "친구 추가를 위한 1 회성 코드를 발급합니다.")
    @Schema(name = "Get Friend Code", description = "친구 코드 발급")
    public ResponseDto<FriendCodeDto> issueFriendCode(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseDto.ok(friendService.issueFriendCode(userId));
    }

    @GetMapping("/code/{friendCode}")
    @Operation(summary = "친구 코드 조회", description = "친구 코드를 입력받아 친구 코드의 정보를 조회 합니다.")
    @Schema(name = "Query FriendCode", description = "친구 코드 조회")
    public ResponseDto<FriendCodeDto> getFriendCode(@PathVariable String friendCode) {
        return ResponseDto.ok(friendService.getFriendCode(friendCode));
    }

    @PostMapping("/code/{friendCode}")
    @Operation(summary = "친구 코드로 친구 추가", description = "친구 코드를 입력받아 친구를 추가합니다.")
    @Schema(name = "Add Friend By Code", description = "친구 코드로 친구 추가")
    public ResponseDto<?> addFriendByCode(@Parameter(hidden = true) @UserId Long userId, @PathVariable String friendCode) {
        friendService.addFriendByCode(userId, friendCode);
        return ResponseDto.ok("친구 추가에 성공하였습니다.");
    }

}
