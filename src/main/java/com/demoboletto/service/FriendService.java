package com.demoboletto.service;

import com.demoboletto.domain.Friend;
import com.demoboletto.domain.FriendCode;
import com.demoboletto.domain.User;
import com.demoboletto.dto.response.FriendCodeDto;
import com.demoboletto.dto.response.FriendResponseDto;
import com.demoboletto.exception.CommonException;
import com.demoboletto.exception.ErrorCode;
import com.demoboletto.repository.FriendCodeRepository;
import com.demoboletto.repository.UserRepository;
import com.demoboletto.repository.friend.FriendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final FriendCodeRepository friendCodeRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final int EXPIRED_DAYS = 2;

    public List<FriendResponseDto> getAllFriends(Long userId) {
        List<Friend> friends = friendRepository.findByUserId(userId);

        return friends.stream()
                .map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<FriendResponseDto> searchFriends(Long userId, String keyword) {

        return friendRepository.findFriendByKeyword(userId, keyword)
                .stream().map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        friendRepository.deleteByFriendUserId(userId, friendId);
    }


    @Transactional
    public FriendCodeDto issueFriendCode(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        FriendCode friendCode = FriendCode.builder()
                .friendCode(issueFriendCode())
                .expiredAt(LocalDate.now().plusDays(EXPIRED_DAYS))
                .user(user)
                .build();
        friendCodeRepository.save(friendCode);

        return FriendCodeDto.of(friendCode);
    }

    public FriendCodeDto getFriendCode(String friendCode) {
        FriendCode code = friendCodeRepository.findByFriendCode(friendCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FRIEND_CODE));
        return FriendCodeDto.of(code);
    }

    @Transactional
    public void addFriendByCode(Long userId, String friendCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        FriendCode code = friendCodeRepository.findByFriendCode(friendCode)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FRIEND_CODE));
        validateFriendCode(code, user);
        code.setUsed(true);
        friendCodeRepository.save(code);
        saveFriends(user, code.getUser());

        alarmService.sendFriendAcceptAlarm(user, code.getUser());
    }

    private void validateFriendCode(FriendCode code, User user) {
        if (LocalDate.now().isAfter(code.getExpiredAt().plusDays(1))) {
            throw new CommonException(ErrorCode.EXPIRED_FRIEND_CODE);
        }
        if (code.isUsed()) {
            throw new CommonException(ErrorCode.USED_FRIEND_CODE);
        }
        if (code.getUser().getId().equals(user.getId())) {
            throw new CommonException(ErrorCode.SELF_FRIEND_CODE);
        }
    }

    private void saveFriends(User user, User friendUser) {
        Friend friend = Friend.builder()
                .user(user)
                .friendUser(friendUser)
                .friendName(friendUser.getName())
                .friendNickname(friendUser.getNickname())
                .friendProfile(friendUser.getUserProfile())
                .build();
        Friend friend2 = Friend.builder()
                .user(friendUser)
                .friendUser(user)
                .friendName(user.getName())
                .friendNickname(user.getNickname())
                .friendProfile(user.getUserProfile())
                .build();
        friendRepository.save(friend);
        friendRepository.save(friend2);
    }


    private String issueFriendCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String friendCode;
        do {
            StringBuilder codeBuilder = new StringBuilder(8);
            for (int i = 0; i < 8; i++) {
                int index = (int) (Math.random() * characters.length());
                codeBuilder.append(characters.charAt(index));
            }
            friendCode = codeBuilder.toString();
        } while (friendCodeRepository.existsByFriendCode(friendCode));
        return friendCode;
    }
}


