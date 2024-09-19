package com.demoboletto.controller;

import com.demoboletto.domain.Friend;
import com.demoboletto.dto.request.FriendRequestDto;
import com.demoboletto.dto.response.FriendResponseDto;
import com.demoboletto.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Friend", description = "친구 관련 API")
@RequestMapping("/api/v1/friend")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("")
    @Operation(summary = "친구 추가", description = "마이페이지에서 친구를 추가합니다.")
    @Schema(name = "Add Friend", description = "친구 목록 추가")
    public void addFriend(@RequestBody FriendRequestDto friendRequestdto) {
        friendService.addFriend(friendRequestdto);
    }

    @GetMapping
    @Operation(summary = "친구 목록 조회", description = "유저의 아이디를 넘겨받아 해당 유저의 친구 목록을 조회합니다.")
    @Schema(name = "Get Friend List", description = "친구 목록 조회")
    public List<FriendResponseDto> getFriends(@RequestParam Long userId) {
        return friendService.getAllFriends(userId);
    }

    @GetMapping("/search")
    @Operation(summary = "친구 검색", description = "친구의 닉네임과 이름으로 친구를 조회합니다.")
    @Schema(name = "Search Friend", description = "특정 친구 조회")
    public List<Friend> searchFriends(@RequestParam String keyword) {
        return friendService.searchFriends(keyword);
    }


    @DeleteMapping("/{userId}")
    @Operation(summary = "친구 삭제", description = "삭제하고 싶은 친구의 유저 id를 받아 특정 친구를 삭제합니다.")
    @Schema(name = "Search Friend", description = "특정 친구 삭제")
    public void deleteFriend(@PathVariable Long userId) {
        friendService.deleteFriend(userId);
    }

}
