package com.demoboletto.service;

import com.demoboletto.domain.Friend;
import com.demoboletto.domain.User;
import com.demoboletto.dto.response.AddFriendResponseDto;
import com.demoboletto.dto.response.FriendResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.friend.FriendRepository;
import com.demoboletto.repository.UserRepository;
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
                .map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<Friend> searchFriends(String keyword) {
        return friendRepository.findFriendByKeyword(keyword);
    }

    public void deleteFriend(Long friendId) {
        friendRepository.deleteByFriendUserId(friendId);
    }

    @Transactional
    public AddFriendResponseDto addFriend(Long userId, Long friendId) {
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

        return AddFriendResponseDto.builder()
                .friendUserId(newFriend.getFriendUser().getId())
                .friendName(newFriend.getFriendName())
                .friendNickName(newFriend.getFriendNickname())
                .friendProfile(newFriend.getFriendProfile())
                .build();
    }

}


