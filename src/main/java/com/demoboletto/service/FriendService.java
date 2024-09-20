package com.demoboletto.service;

import com.demoboletto.domain.Friend;
import com.demoboletto.domain.User;
import com.demoboletto.dto.global.ResponseDto;
import com.demoboletto.dto.request.FriendRequestDto;
import com.demoboletto.dto.response.FriendResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.FriendRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.type.EFriendType;
import com.demoboletto.type.EProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public List<FriendResponseDto> getAllFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);

        return friends.stream()
                .map(friend -> new FriendResponseDto(
                        friend.getFriendUser().getId(),
                        friend.getFriendNickname(),
                        friend.getFriendName(),
                        friend.getFriendProfile()
                ))
                .collect(Collectors.toList());
    }

    // 친구 이름 또는 닉네임으로 검색
    public List<Friend> searchFriends(String keyword) {
        return friendRepository.findByFriendNameContainingOrFriendNicknameContaining(keyword, keyword);
    }

    @Transactional
    public void deleteFriend(Long friendId) {
        friendRepository.deleteByFriendUserId(friendId);
    }

    @Transactional
    public Friend addFriend(FriendRequestDto friendRequest) {
        User user = userRepository.findById(friendRequest.userId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        User friendUser = userRepository.findById(friendRequest.friendUserId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        Friend newFriend = Friend.builder()
                .user(user)
                .friendUser(friendUser)
                .friendName(friendRequest.friendName())
                .friendNickname(friendRequest.friendNickname())
                .friendProfile(friendRequest.friendProfile())
                .build();

        friendRepository.save(newFriend);
        return newFriend;
    }

}


