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
                .map(friend -> {
                    Long friendUserId = friend.getFriendUser().getId();
                    boolean isFriend = friendRepository.existsByUserIdAndFriendUserId(userId, friendUserId);
                    return new FriendResponseDto(
                            friendUserId,
                            friend.getFriendNickname(),
                            friend.getFriendName(),
                            friend.getFriendProfile()
                    );
                })
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
    public Friend addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        User friendUser = userRepository.findById(friendId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        boolean isAlreadyFriend = friendRepository.existsByUserIdAndFriendUserId(userId, friendId);
        if (isAlreadyFriend) {
            throw new IllegalArgumentException("Friend has already been added.");
        }

        Friend newFriend = Friend.builder()
                .user(user)
                .friendUser(friendUser)
                .friendName(friendUser.getName())
                .friendNickname(friendUser.getNickname())
                .friendProfile(friendUser.getUserProfile())
                .build();

        friendRepository.save(newFriend);

        return newFriend;
    }

}


